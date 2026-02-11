package com.liceolapaz.bcdnob.ahorca_do.model;

public class PlayState {
    private static final long serialVersionUID = 1L;

    private String progress;
    private int lives;
    private boolean isYourTurn;
    private boolean isFinished;
    private String mesaje;

    public PlayState(String progress, int lives, boolean isYourTurn, boolean isFinished, String mesaje) {
        this.progress = progress;
        this.lives = lives;
        this.isYourTurn = isYourTurn;
        this.isFinished = isFinished;
        this.mesaje = mesaje;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getMesaje() {
        return mesaje;
    }

    public void setMesaje(String mesaje) {
        this.mesaje = mesaje;
    }
}
