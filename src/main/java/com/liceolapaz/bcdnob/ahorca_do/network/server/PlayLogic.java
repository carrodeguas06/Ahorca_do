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
    private StringBuilder failedLettersStr = new StringBuilder();

    public PlayLogic(String palabra, int numJugadores) {
        this.nPlayers = numJugadores;
        // Chivato en consola
        System.out.println("=========================================");
        System.out.println("[SERVIDOR] NUEVA PARTIDA GENERADA");
        System.out.println("[SERVIDOR] PALABRA SECRETA: " + palabra.toUpperCase());
        System.out.println("=========================================");

        newRound(palabra);
    }

    public synchronized void newRound(String nuevaPalabra) {
        this.secretWord = nuevaPalabra.toUpperCase();
        this.progress = "_".repeat(secretWord.length());
        this.failedLettersStr.setLength(0);

        // CALCULO DE VIDAS: Mitad de la longitud, mínimo 1
        int vidasIniciales = Math.max(1, secretWord.length() / 2);

        for (int i = 0; i < nPlayers; i++) {
            playerLives.put(i, vidasIniciales);
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

            if (failedLettersStr.indexOf(String.valueOf(letra)) == -1) {
                if (!failedLettersStr.isEmpty()) failedLettersStr.append(" ");
                failedLettersStr.append(letra);
            }

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
    public String getSecretWord() { return secretWord; }
    public String getFailedLetters() { return failedLettersStr.toString(); }
}