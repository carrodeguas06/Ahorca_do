package com.liceolapaz.bcdnob.ahorca_do.network.server;

import com.liceolapaz.bcdnob.ahorca_do.dao.PartidaDAO;
import com.liceolapaz.bcdnob.ahorca_do.model.PlayState;
import com.liceolapaz.bcdnob.ahorca_do.model.User;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ThreadClient implements Runnable {
    private SSLSocket socket;
    private int idPropio;
    private PlayLogic play;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User user;
    private String endMessage = "PARTIDA FINALIZADA (Sin vidas)";

    public ThreadClient(SSLSocket s, int id, PlayLogic p, ObjectOutputStream out, ObjectInputStream in, User user) {
        this.socket = s;
        this.idPropio = id;
        this.play = p;
        this.out = out;
        this.in = in;
        this.user = user;
    }

    @Override
    public void run() {
        try {
            while (play.isActiv()) {
                sendState(false, "");

                synchronized (play) {
                    while (play.getTurn() != idPropio && play.isActiv()) {
                        play.wait();
                        sendState(false, "");
                    }
                }

                if (!play.isActiv()) break;

                Object msg = in.readObject();

                if (msg instanceof Character) {
                    play.processPlay(idPropio, (Character) msg);
                    // Comprobar victoria
                    if (!play.getProgress().contains("_")) {
                        endMessage = "¡HAS GANADO!";
                        play.cancelPlay();
                        synchronized (play) { play.notifyAll(); }
                    }
                }
                else if (msg instanceof String) {
                    String texto = (String) msg;
                    if ("PUNTUACION".equals(texto)) {
                        long puntos = new PartidaDAO().obtenerPuntuacionTotal(user.getId());
                        out.writeObject("PUNTUACION_DATA:" + user.getNickname() + ": " + puntos + " pts");
                        out.flush();
                    } else if ("CANCELAR".equals(texto)) {
                        play.cancelPlay();
                        synchronized (play) { play.notifyAll(); }
                    }
                }
            }
            // Enviar estado final de victoria o derrota
            sendState(true, endMessage);

        } catch (Exception e) {
            System.err.println("Error en HiloCliente " + idPropio + ": " + e.getMessage());
        } finally {
            unconect();
        }
    }

    private void sendState(boolean finished, String msg) throws IOException {
        if (socket.isClosed()) return;
        PlayState ps = new PlayState(
                play.getProgress(),
                play.getVidas(idPropio),
                play.getTurn() == idPropio,
                finished,
                msg,
                play.getFailedLetters()
        );
        out.writeObject(ps);
        out.flush();
        out.reset();
    }

    private void unconect() {
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {}
    }
}