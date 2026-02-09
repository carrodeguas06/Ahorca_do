package com.liceolapaz.bcdnob.ahorca_do.navigation;

public enum AppView {

    AHORCADO("/com/liceolapaz/bcdnob/ahorca_do/ahorcado-view.fxml", "Ahorca.do"),

    LOGIN("/com/liceolapaz/bcdnob/ahorca_do/logIn-view.fxml", "Iniciar Sesión"),
    MENU("/com/liceolapaz/bcdnob/ahorca_do/menu-view.fxml", "Menú de juego");

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