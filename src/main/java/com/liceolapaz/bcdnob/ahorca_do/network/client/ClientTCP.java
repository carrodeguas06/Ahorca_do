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
            // 1. CARGAR EL ARCHIVO DE PROPIEDADES (Esto es lo que te faltaba y daba error)
            InputStream propStream = getClass().getResourceAsStream("/.properties");
            if (propStream == null) {
                throw new FileNotFoundException("No se encuentra el archivo .properties");
            }
            prop.load(propStream);

            // 2. CONFIGURAR SSL USANDO STREAMS (Más seguro que System.setProperty para recursos)
            String trustStorePath = prop.getProperty("keyPath");
            String trustStorePass = prop.getProperty("keyPassword");

            // Cargar el TrustStore desde los recursos (dentro del JAR/proyecto)
            KeyStore trustStore = KeyStore.getInstance("JKS");
            InputStream tsStream = getClass().getResourceAsStream(trustStorePath);
            if (tsStream == null) {
                throw new FileNotFoundException("No se encuentra el archivo de claves en: " + trustStorePath);
            }
            trustStore.load(tsStream, trustStorePass.toCharArray());

            // Inicializar TrustManager
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            // Crear Contexto SSL
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            // 3. OBTENER DATOS DE CONEXIÓN
            // Usamos "localhost" por defecto si no está en el properties
            String host = prop.getProperty("server.host", "localhost");
            // Corregimos la clave: en tu archivo es "port", no "server.port"
            int port = Integer.parseInt(prop.getProperty("port"));

            // 4. CREAR SOCKET
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            socket = (SSLSocket) sslSocketFactory.createSocket(host, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (Exception e) {
            System.err.println("Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void enviarDatos(Object datos) {
        try {
            if (out != null) {
                out.writeObject(datos);
                out.flush();
                out.reset(); // Importante para evitar caché de objetos al enviar repetidamente
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