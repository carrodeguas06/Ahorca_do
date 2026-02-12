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

    private ClientTCP client;
    private boolean escuchando = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User currentUser = AppShell.getInstance().getCurrentUser();
        lblNombreJugador1.setText(currentUser.getNickname());
        lblNombreJugador2.setText("Esperando...");

        new Thread(() -> startConnection(currentUser)).start();
    }
    private void startConnection(User user) {
        try {
            client = new ClientTCP();
            client.conectar();

            int mode = AppShell.getInstance().getGameMode();
            client.enviarDatos(mode);

            client.enviarDatos(user);
            client = new ClientTCP();
            client.conectar();

            client.enviarDatos(user);

            while (escuchando) {
                Object recibido = client.recibirDatos();

                if (recibido instanceof PlayState) {
                    PlayState estado = (PlayState) recibido;

                    Platform.runLater(() -> actualizarInterfaz(estado));

                    if (estado.isFinished()) {
                        escuchando = false;
                        Platform.runLater(() -> mostrarFinPartida(estado.getMesaje()));
                    }
                }
                else if (recibido instanceof String) {
                    String msg = (String) recibido;
                    if(msg.startsWith("PUNTUACION:")) {
                        Platform.runLater(() -> mostrarAlerta("Puntuación", msg));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                lblMensaje.setText("Error de conexión con el servidor.");
                btnEnviar.setDisable(true);
            });
        }
    }

    private void actualizarInterfaz(PlayState estado) {
        String palabraFormateada = estado.getProgress().replace("", " ").trim();
        lblPalabraOculta.setText(palabraFormateada);
        lblIntentos.setText(String.valueOf(estado.getLives()));

        if (estado.isYourTurn()) {
            lblMensaje.setText("¡Es tu turno! Escribe una letra.");
            txtLetra.setDisable(false);
            btnEnviar.setDisable(false);
            txtLetra.requestFocus();
        } else {
            lblMensaje.setText("Esperando turno del oponente...");
            txtLetra.setDisable(true);
            btnEnviar.setDisable(true);
        }
    }

    public void onEnviarLetra(ActionEvent actionEvent) {
        String texto = txtLetra.getText();

        if (texto != null && !texto.isEmpty()) {
            char letra = texto.charAt(0);
            client.enviarDatos(letra);

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de la partida");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        onCancelar(null);
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
