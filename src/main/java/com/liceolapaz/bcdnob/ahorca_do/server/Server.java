package com.liceolapaz.bcdnob.ahorca_do.server;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class Server {
    static final int MAXIMO = 10;

    public static void main(String args[]) throws IOException {
        int PUERTO = 65000;

        // CONFIGURACIÓN DE CRIPTOGRAFÍA
        System.setProperty("javax.net.ssl.keyStore", "key/key.jks");
        Properties prop = new Properties();
        prop.load(Server.class.getClassLoader().getResourceAsStream(".properties"));
        System.setProperty("javax.net.ssl.keyStorePassword", prop.getProperty("keyPassword"));

        // Factoría de sockets seguros
        SSLServerSocketFactory sssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        try (SSLServerSocket servidor = (SSLServerSocket) sssf.createServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado...");

            Socket tabla[] = new Socket[MAXIMO];
            ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla);

            while (comun.getCONEXIONES() < MAXIMO) {
                // El servidor acepta conexiones SSL
                SSLSocket socket = (SSLSocket) servidor.accept();

                comun.addTabla(socket, comun.getCONEXIONES());
                comun.setACTUALES(comun.getACTUALES() + 1);
                comun.setCONEXIONES(comun.getCONEXIONES() + 1);

                HiloServidorChat hilo = new HiloServidorChat(socket, comun);
                hilo.start();
            }
        }
    }
}
