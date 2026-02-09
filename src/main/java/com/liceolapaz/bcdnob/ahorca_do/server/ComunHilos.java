package com.liceolapaz.bcdnob.ahorca_do.server;

import java.net.Socket;

public class ComunHilos {
     int CONEXIONES;
     int ACTUALES;
     int MAXIMO;
     Socket[] tabla;
     String mensajes;
     public ComunHilos(int maximo, int actuales, int conexiones, Socket[] tabla) {
         this.MAXIMO = maximo;
         this.ACTUALES = actuales;
         this.CONEXIONES = conexiones;
         this.tabla = tabla;
         this.mensajes = "";
     }

     public ComunHilos() {}

     public synchronized int getCONEXIONES() {
         return CONEXIONES;
     }

     public synchronized void setCONEXIONES(int conexiones) {
         CONEXIONES = conexiones;
     }

     public synchronized String getMensajes() {
         return mensajes;
     }

     public synchronized void setMensajes(String mensajes) {
         this.mensajes = mensajes;
     }

     public synchronized int getACTUALES() {
         return ACTUALES;
     }

     public synchronized void setACTUALES(int actuales) {
         ACTUALES = actuales;
     }

     public synchronized void addTabla(Socket s, int i) {
         tabla[i] = s;
     }

     public synchronized Socket getElementoTabla(int i) {
         return tabla[i];
     }
 }
