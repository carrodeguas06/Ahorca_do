package com.liceolapaz.bcdnob.ahorca_do.controllers;

import com.liceolapaz.bcdnob.ahorca_do.navigation.AppShell;
import com.liceolapaz.bcdnob.ahorca_do.navigation.AppView;
import com.liceolapaz.bcdnob.ahorca_do.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    public TextField txtNombre;
    @FXML
    public TextField txtApellido;
    @FXML
    public TextField txtNickname;
    @FXML
    public PasswordField txtPassword;

    UserService userService = new UserService();

    public void onVolver(ActionEvent actionEvent) {
        AppShell.getInstance().loadView(AppView.LOGIN);
    }

    public void onRegistrar(ActionEvent actionEvent) throws Exception {
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtNickname.getText().isEmpty() || txtPassword.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Es necesario cubrir todos los campos");
            alert.showAndWait();
            return;
        }
        else
        {
            userService.registrarusuario(txtNombre.getText(),txtApellido.getText(),txtNickname.getText(),txtPassword.getText());
        }
    }
}
