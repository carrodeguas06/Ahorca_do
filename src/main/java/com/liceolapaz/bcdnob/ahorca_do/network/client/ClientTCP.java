package com.liceolapaz.bcdnob.ahorca_do.network.client;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.util.Properties;

public class ClientTCP {
    private SSLSocket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Properties prop = new Properties();

    public void conectar() {
        String trustStorePath = prop.getProperty("keyPath");
        String trustStorePass =  prop.getProperty("keyPassword");

        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePass);

        try {
            String host = prop.getProperty("server.host");
            int port = Integer.parseInt(prop.getProperty("server.port"));

            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket = (SSLSocket) sslSocketFactory.createSocket(host, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }

    public void enviarDatos(Object datos) {
        try {
            if (out != null) {
                out.writeObject(datos);
                out.flush();
                out.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object recibirDatos() {
        try {
            if (in != null) {
                return in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void desconectar() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
