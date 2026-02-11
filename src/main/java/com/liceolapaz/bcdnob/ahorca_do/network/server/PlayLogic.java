package com.liceolapaz.bcdnob.ahorca_do.network.server;

import java.util.HashMap;
import java.util.Map;

public class PlayLogic {
    private String secretWord;
    private String progress;
    private int turn = 0;
    private int nPlayers;
    private boolean activ = true;
    private Map<Integer, Integer> playerLives = new HashMap<>();

    public PlayLogic(String palabra, int numJugadores) {
        this.nPlayers = numJugadores;
        iniciarNuevaRonda(palabra);
    }

    public synchronized void iniciarNuevaRonda(String nuevaPalabra) {
        this.secretWord = nuevaPalabra.toUpperCase();
        this.progress = "_".repeat(secretWord.length());
        for (int i = 0; i < nPlayers; i++) {
            playerLives.put(i, 6);
        }
        this.activ = true;
    }

    public synchronized void cancelPlay() {
        this.activ = false;
    }

    public synchronized void processPlay(int id, char letra) {
        if (id != turn || !activ) return;

        letra = Character.toUpperCase(letra);
        boolean acierto = false;
        StringBuilder nuevoProgreso = new StringBuilder(progress);

        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letra) {
                nuevoProgreso.setCharAt(i, letra);
                acierto = true;
            }
        }

        if (acierto) {
            progress = nuevoProgreso.toString();
        } else {
            int v = playerLives.get(id) - 1;
            playerLives.put(id, v);

            if (v <= 0) {
                activ = false;
            } else if (nPlayers == 2) {
                this.turn = (this.turn == 0) ? 1 : 0;
            }
        }
    }

    public String getProgress() { return progress; }
    public int getVidas(int id) { return playerLives.getOrDefault(id, 0); }
    public int getTurn() { return turn; }
    public boolean isActiv() { return activ; }
}
