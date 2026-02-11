package com.liceolapaz.bcdnob.ahorca_do.network.server;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServerSSL {
    private Properties prop = new Properties();
    public void iniciar() {
        try {
            WordDao.importarDesdeJson();
            SSLContext sc = crearContextoSSL();
            SSLServerSocket serverSocket = (SSLServerSocket) sc.getServerSocketFactory().createServerSocket(Integer.parseInt(prop.getProperty("server.port")));
            System.out.println("[SERVIDOR] Listo.");

            while (true) {
                List<SSLSocket> clientes = new ArrayList<>();
                SSLSocket s1 = (SSLSocket) serverSocket.accept();
                clientes.add(s1);
                serverSocket.setSoTimeout(10000);
                try { clientes.add((SSLSocket) serverSocket.accept()); } catch (Exception e) {}
                serverSocket.setSoTimeout(0);

                PlayLogic logica = new PlayLogic(WordDao.getPalabraSecreta(), clientes.size());
                for (int i = 0; i < clientes.size(); i++) {
                    new Thread(new ThreadClient(clientes.get(i), i, logica)).start();
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private SSLContext crearContextoSSL() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(prop.getProperty("keysPath")), prop.getProperty("keyPassword").toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, prop.getProperty("keyPassword").toCharArray());
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(kmf.getKeyManagers(), null, null);
        return sc;
    }

    public static void main(String[] args) { new ServerSSL().iniciar(); }
}
