package com.liceolapaz.bcdnob.ahorca_do.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloServidor extends Thread {
    DataInputStream flujoentrada;
    Socket socket = null;
    ComunHilos comun;

    public HiloServidor(Socket s, ComunHilos comun) {
        this.socket = s;
        this.comun = comun;
        try {
            flujoentrada = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String cadena = flujoentrada.readUTF();

                if (cadena.trim().equals("*")) {
                    comun.setACTUALES(comun.getACTUALES() - 1);
                    break;
                }

                synchronized (comun) {
                    comun.setMensajes(comun.getMensajes() + cadena + "\n");
                    EnviarMensajesaTodos(cadena);
                }

            } catch (IOException e) {
                break;
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void EnviarMensajesaTodos(String texto) {
        for (int i = 0; i < comun.getCONEXIONES(); i++) {
            Socket s1 = comun.getElementoTabla(i);
            if (s1 != null && !s1.isClosed()) {
                try {
                    DataOutputStream flujosalida = new DataOutputStream(s1.getOutputStream());
                    flujosalida.writeUTF(texto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}