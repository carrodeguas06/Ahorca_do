package com.liceolapaz.bcdnob.ahorca_do.network.server;

import com.liceolapaz.bcdnob.ahorca_do.model.User;
import com.liceolapaz.bcdnob.ahorca_do.model.Word;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServerSSL {
    private Properties prop = new Properties();

    private List<PendingPlayer> waitingList = new ArrayList<>();

    private static class PendingPlayer {
        SSLSocket socket;
        ObjectOutputStream out;
        ObjectInputStream in;
        User user;

        public PendingPlayer(SSLSocket s, ObjectOutputStream out, ObjectInputStream in, User u) {
            this.socket = s; this.out = out; this.in = in; this.user = u;
        }
    }

    public void iniciar() {
        try {
            prop.load(getClass().getResourceAsStream("/.properties"));

            SSLContext sc = crearContextoSSL();
            SSLServerSocket serverSocket = (SSLServerSocket) sc.getServerSocketFactory()
                    .createServerSocket(Integer.parseInt(prop.getProperty("port")));

            System.out.println("[SERVIDOR] Esperando conexiones en puerto " + prop.getProperty("port"));

            while (true) {
                SSLSocket socket = (SSLSocket) serverSocket.accept();

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                int mode = (int) in.readObject();
                User user = (User) in.readObject();

                System.out.println("Conexión: " + user.getNickname() + " Modo: " + mode);

                if (mode == 1) {

                    PlayLogic logica = new PlayLogic(Word.getSecretWord(), 1);
                    new Thread(new ThreadClient(socket, 0, logica, out, in, user)).start();
                }
                else {

                    waitingList.add(new PendingPlayer(socket, out, in, user));


                    if (waitingList.size() >= 2) {
                        iniciarPartidaMultijugador();
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void iniciarPartidaMultijugador() {

        PendingPlayer p1 = waitingList.remove(0);
        PendingPlayer p2 = waitingList.remove(0);

        PlayLogic logica = new PlayLogic(Word.getSecretWord(), 2);


        new Thread(new ThreadClient(p1.socket, 0, logica, p1.out, p1.in, p1.user)).start();
        new Thread(new ThreadClient(p2.socket, 1, logica, p2.out, p2.in, p2.user)).start();

        System.out.println("Partida Multijugador iniciada: " + p1.user.getNickname() + " vs " + p2.user.getNickname());
    }

    private SSLContext crearContextoSSL() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");

        ks.load(getClass().getResourceAsStream(prop.getProperty("keyPath")), prop.getProperty("keyPassword").toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, prop.getProperty("keyPassword").toCharArray());

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(kmf.getKeyManagers(), null, null);
        return sc;
    }

    public static void main(String[] args) { new ServerSSL().iniciar(); }
}