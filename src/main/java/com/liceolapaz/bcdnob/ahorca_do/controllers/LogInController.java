package com.liceolapaz.bcdnob.ahorca_do.controllers;

import com.liceolapaz.bcdnob.ahorca_do.model.User;
import com.liceolapaz.bcdnob.ahorca_do.navigation.AppShell;
import com.liceolapaz.bcdnob.ahorca_do.navigation.AppView;
import com.liceolapaz.bcdnob.ahorca_do.service.UserService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    public TextField user;
    @FXML
    public PasswordField password;
    @FXML
    public Button boton;
    @FXML
    public Button register;

    private UserService usuarioService = new UserService();

    public void onClic(ActionEvent actionEvent) {
        String username = user.getText();
        String pass = password.getText();

        if (username.isEmpty() || pass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor compruebe que todos los campos están cubiertos");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText("Conectando...");
        alert.show();
        setInputsDisabled(true);

        Task<Optional<User>> loginTask = new Task<>() {
            @Override
            protected Optional<User> call() throws Exception {
                return usuarioService.login(username, pass);
            }
        };
        loginTask.setOnSucceeded(event -> {
            setInputsDisabled(false);
            Optional<User> usuarioOpt = loginTask.getValue();

            if (usuarioOpt.isPresent()) {
                User user = usuarioOpt.get();

                alert.setTitle("Conectado");
                alert.setHeaderText("Se ha conectado con éxito");
                alert.show();

                AppShell.getInstance().setCurrentUser(user);

                AppShell.getInstance().loadView(AppView.MENU);

            } else {
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Error");
                alert1.setHeaderText("La contraseña o el usuario son incorrectos");
                alert1.show();
            }
        });

        loginTask.setOnFailed(event -> {
            setInputsDisabled(false);
            Alert aler = new Alert(Alert.AlertType.ERROR);
            aler.setTitle("Error");
            aler.setHeaderText(null);
            aler.setContentText("Error al conectar con el servidor");
            aler.showAndWait();
            Throwable ex = loginTask.getException();
            ex.printStackTrace();
        });

        new Thread(loginTask).start();
    }

    private void setInputsDisabled(boolean disabled) {
        user.setDisable(disabled);
        password.setDisable(disabled);
    }

    public void registerClic(ActionEvent actionEvent) {
        AppShell.getInstance().loadView(AppView.REGISTER);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
