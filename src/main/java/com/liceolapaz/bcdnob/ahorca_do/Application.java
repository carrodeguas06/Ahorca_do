package com.liceolapaz.bcdnob.ahorca_do;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ahorcado-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Error al cargar el icono");
            e.printStackTrace();
        }
        stage.setTitle("Ahorca.do");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        String cssPath = getClass().getResource("/styles/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
    }
}
