package com.liceolapaz.bcdnob.ahorca_do.network.server;

import com.liceolapaz.bcdnob.ahorca_do.model.User;
import com.liceolapaz.bcdnob.ahorca_do.model.Word;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ThreadClient implements Runnable {
    private SSLSocket socket;
    private int idPropio;
    private PlayLogic play;
    private ObjectOutputStream out;
    private User user;

    public ThreadClient(SSLSocket s, int id, PlayLogic p) {
        this.socket = s; this.idPropio = id; this.play = p;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            this.user = (User) in.readObject();

            while (play.isActiv()) {
                sendState();

                synchronized (play) {
                    while (play.getTurn() != idPropio && play.isActiv()) {
                        play.wait();
                        sendState();
                    }
                }

                if (!play.isActiv()) break;

                Object msg = in.readObject();

                if (msg instanceof Character) {
                    play.processPlay(idPropio, (Character) msg);

                    if (!play.getProgress().contains("_")) {
                        String nueva = Word.getSecretWord();
                        synchronized (play) {
                            play.newRound(nueva);
                            play.notifyAll();
                        }
                    } else {
                        synchronized (play) {
                            play.notifyAll();
                        }
                    }
                }
                else if (msg instanceof String) {
                    String texto = (String) msg;
                    if ("PUNTUACION".equals(texto)) {
                        long puntos = new PartidaDAO().obtenerPuntuacionTotal(user.getId());
                        out.writeObject("PUNTUACION:" + puntos);
                        out.flush();
                    } else if ("CANCELAR".equals(texto)) {
                        play.cancelPlay();
                        synchronized (play) { play.notifyAll(); }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error en HiloCliente " + idPropio + ": " + e.getMessage());
        } finally {
            unconect();
        }
    }

    private void sendState() throws IOException {
        if (socket.isClosed()) return;
        PlayState ps = new PlayState(
                play.getProgress(),
                play.getVidas(idPropio),
                play.getTurn() == idPropio,
                !play.isActiv(),
                !play.isActiv() ? "PARTIDA FINALIZADA" : ""
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
