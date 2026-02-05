package com.liceolapaz.bcdnob.ahorca_do.navigation;

public enum AppView {

    AHORCADO("", "Juego Ahorcado"),

    LOGIN("/com/liceolapaz/bcdnob/ahorca_do/controllers/logIn-view.fxml", "Login System");

    private final String fxmlPath;
    private final String title;

    AppView(String fxmlPath, String title) {
        this.fxmlPath = fxmlPath;
        this.title = title;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public String getTitle() {
        return title;
    }
}