package com.liceolapaz.bcdnob.ahorca_do.controllers;

import com.liceolapaz.bcdnob.ahorca_do.model.PlayState;
import com.liceolapaz.bcdnob.ahorca_do.model.User;
import com.liceolapaz.bcdnob.ahorca_do.navigation.AppShell;
import com.liceolapaz.bcdnob.ahorca_do.navigation.AppView;
import com.liceolapaz.bcdnob.ahorca_do.network.client.ClientTCP;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML public Label lblNombreJugador1;
    @FXML public Label lblNombreJugador2;
    @FXML public Label j2l;
    @FXML public Label lblIntentos;
    @FXML public Label lblPalabraOculta;
    @FXML public Label lblMensaje;
    @FXML public TextFlow flowFallos;
    @FXML public TextField txtLetra;
    @FXML public Button btnEnviar;

    private ClientTCP client;
    private boolean escuchando = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User currentUser = AppShell.getInstance().getCurrentUser();
        lblNombreJugador1.setText(currentUser.getNickname());
        if (AppShell.getInstance().getGameMode() == 1) {
            j2l.setVisible(false);
            lblNombreJugador2.setVisible(false);
        } else {
            lblNombreJugador2.setText("Esperando...");
        }
        new Thread(() -> startConnection(currentUser)).start();
    }

    private void startConnection(User user) {
        try {
            client = new ClientTCP();
            client.conectar();
            client.enviarDatos(AppShell.getInstance().getGameMode());
            client.enviarDatos(user);

            while (escuchando) {
                Object recibido = client.recibirDatos();
                if (recibido == null) break;

                if (recibido instanceof PlayState) {
                    PlayState estado = (PlayState) recibido;
                    Platform.runLater(() -> actualizarInterfaz(estado));
                    if (estado.isFinished()) {
                        escuchando = false;
                        Platform.runLater(() -> mostrarFinPartida(estado.getMesaje()));
                    }
                } else if (recibido instanceof String) {
                    String msg = (String) recibido;
                    if(msg.startsWith("PUNTUACION_DATA:")) {
                        String contenido = msg.substring("PUNTUACION_DATA:".length());
                        Platform.runLater(() -> mostrarAlerta("Puntos", contenido));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizarInterfaz(PlayState estado) {
        lblPalabraOculta.setText(estado.getProgress().replace("", " ").trim());
        lblIntentos.setText(String.valueOf(estado.getLives()));
        flowFallos.getChildren().clear();
        if (estado.getFailedLetters() != null && !estado.getFailedLetters().isEmpty()) {
            Text f = new Text(estado.getFailedLetters());
            f.setFill(Color.RED);
            flowFallos.getChildren().add(f);
        }
        if (!estado.isFinished()) {
            if (estado.isYourTurn()) {
                lblMensaje.setText("¡Es tu turno!");
                txtLetra.setDisable(false);
                btnEnviar.setDisable(false);
            } else {
                lblMensaje.setText("Esperando...");
                txtLetra.setDisable(true);
                btnEnviar.setDisable(true);
            }
        }
    }

    public void onEnviarLetra(ActionEvent actionEvent) {
        String t = txtLetra.getText();
        if (t != null && !t.isEmpty()) {
            client.enviarDatos(t.charAt(0));
            txtLetra.clear();
        }
    }

    public void onVerPuntuacion(ActionEvent actionEvent) {
        client.enviarDatos("PUNTUACION");
    }

    public void onCancelar(ActionEvent actionEvent) {
        escuchando = false;
        if (client != null) {
            client.enviarDatos("CANCELAR");
            client.desconectar();
        }
        AppShell.getInstance().loadView(AppView.MENU);
    }

    private void mostrarFinPartida(String mensaje) {
        lblMensaje.setText(mensaje);
        txtLetra.setDisable(true);
        btnEnviar.setDisable(true);
        mostrarAlerta("Juego Terminado", mensaje);
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(contenido);
        a.showAndWait();
    }
}