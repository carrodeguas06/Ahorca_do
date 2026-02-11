package com.liceolapaz.bcdnob.ahorca_do.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    public Label lblNombreJugador1;
    @FXML
    public Label lblNombreJugador2;
    @FXML
    public Label j2l;
    @FXML
    public Label lblIntentos;
    @FXML
    public Label lblPalabraOculta;
    @FXML
    public Label lblMensaje;
    @FXML
    public TextFlow flowFallos;
    @FXML
    public TextField txtLetra;
    @FXML
    public Button btnEnviar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onEnviarLetra(ActionEvent actionEvent) {
    }

    public void onVerPuntuacion(ActionEvent actionEvent) {
    }

    public void onCancelar(ActionEvent actionEvent) {
    }
}
