package com.tbdbookstore.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    private static StackPane root;

    public static void main(String[] args) {
        launch(args);
    }

    public static StackPane getRoot() {
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/com/tbdbookstore/view/fxml/manager/Manager.fxml"));
        Scene scene = new Scene(root, 1280, 800);
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
