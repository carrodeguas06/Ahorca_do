package com.liceolapaz.bcdnob.ahorca_do.controllers;

import com.liceolapaz.bcdnob.ahorca_do.navigation.AppShell;
import com.liceolapaz.bcdnob.ahorca_do.navigation.AppView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handlePlay(ActionEvent actionEvent) {
        AppShell.getInstance().loadView(AppView.LOGIN);
    }

    public void handleJugar(ActionEvent actionEvent) {

    }

    public void handleSearchFriend(ActionEvent actionEvent) {
    }
}
