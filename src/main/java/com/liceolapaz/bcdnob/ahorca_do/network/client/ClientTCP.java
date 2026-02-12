package com.liceolapaz.bcdnob.ahorca_do.network.client;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.util.Properties;

public class ClientTCP {
    private SSLSocket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Properties prop = new Properties();

    public void conectar() {
        try {
            InputStream propStream = getClass().getResourceAsStream("/.properties");
            if (propStream == null) throw new FileNotFoundException("No se encuentra .properties");
            prop.load(propStream);

            String trustStorePath = prop.getProperty("keyPath");
            String trustStorePass = prop.getProperty("keyPassword");

            KeyStore trustStore = KeyStore.getInstance("JKS");
            InputStream tsStream = getClass().getResourceAsStream(trustStorePath);
            if (tsStream == null) throw new FileNotFoundException("No se encuentra key.jks");
            trustStore.load(tsStream, trustStorePass.toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            String host = prop.getProperty("server.host", "localhost");
            int port = Integer.parseInt(prop.getProperty("port"));

            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            socket = (SSLSocket) sslSocketFactory.createSocket(host, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (Exception e) {
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
        }
    }

    public Object recibirDatos() {
        try {
            if (in != null) {
                return in.readObject();
            }
        } catch (EOFException e) {

            return null;
        } catch (IOException | ClassNotFoundException e) {
            if (socket != null && !socket.isClosed()) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void desconectar() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) { }
    }
}