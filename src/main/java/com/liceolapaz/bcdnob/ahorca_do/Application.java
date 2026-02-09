package com.liceolapaz.bcdnob.ahorca_do;

import com.liceolapaz.bcdnob.ahorca_do.navigation.AppShell;
import com.liceolapaz.bcdnob.ahorca_do.navigation.AppView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {

        AppShell.getInstance().setPrimaryStage(stage);


        stage.setTitle("Ahorca.do");
        stage.setResizable(false);

        Scene scene = new Scene(new Pane(), 800, 500);

        try {
            String cssPath = getClass().getResource("/styles/styles.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
        }

        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {
        }

        stage.setScene(scene);
        stage.show();

        AppShell.getInstance().loadView(AppView.AHORCADO);
    }
}
