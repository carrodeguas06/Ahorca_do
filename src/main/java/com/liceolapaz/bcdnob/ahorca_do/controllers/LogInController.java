package com.liceolapaz.bcdnob.ahorca_do.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LogInController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
