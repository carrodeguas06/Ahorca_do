package com.liceolapaz.bcdnob.ahorca_do.navigation;

import com.liceolapaz.bcdnob.ahorca_do.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppShell {
    private static AppShell instance;
    private Stage primaryStage;
    private BorderPane mainLayout;
    private Map<AppView, Object> controllers = new HashMap<>();

    private User currentUser;

    private AppShell() {}

    public static AppShell getInstance() {
        if (instance == null) {
            instance = new AppShell();
        }
        return instance;
    }

    public void init(Stage stage) {
        this.primaryStage = stage;
        this.mainLayout = new BorderPane();
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
    }


    public Object loadView(AppView view) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(view.getFxmlPath()));
            Parent viewNode = loader.load();
            try {
                String cssPath = getClass().getResource("/styles/styles.css").toExternalForm();
                viewNode.getStylesheets().add(cssPath);
            } catch (NullPointerException e) {
            }

            primaryStage.setTitle(view.getTitle());


            primaryStage.getScene().setRoot(viewNode);

            primaryStage.show();
            primaryStage.centerOnScreen();

            Object controller = loader.getController();

            controllers.put(view, controller);


            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
}