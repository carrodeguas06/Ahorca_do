package com.liceolapaz.bcdnob.ahorca_do.navigation;

public enum AppView {

    AHORCADO("/com/liceolapaz/bcdnob/ahorca_do/ahorcado-view.fxml", "Ahorca.do"),
    REGISTER("/com/liceolapaz/bcdnob/ahorca_do/register-view.fxml", "Registrate"),
    LOGIN("/com/liceolapaz/bcdnob/ahorca_do/logIn-view.fxml", "Iniciar Sesión"),
    MENU("/com/liceolapaz/bcdnob/ahorca_do/menu-view.fxml", "Menú de juego"),

    GAME("/com/liceolapaz/bcdnob/ahorca_do/game-view.fxml", "Pantalla juego")

    ;



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