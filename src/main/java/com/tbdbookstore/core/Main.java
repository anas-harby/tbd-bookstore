package com.tbdbookstore.core;

import com.tbdbookstore.core.jasperreports.Report;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

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

        /*for jasper trial only*/ //TODO remove later
//        Connection connection = connectToDatabase("jdbc:mysql://localhost:3306/BOOKSTORE", "root", "sara1201");
//
//        File file = new File("./src/main/resources/com/tbdbookstore/view/jrxml/trial.jrxml");
//        Report report = new Report(file.getAbsolutePath(), connection);
    }

    public static Connection connectToDatabase(String databaseName, String userName, String password) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(databaseName, userName, password);
        } catch (Exception e) {
            String text = "Could not connect to the database: " + e.getMessage() + " "
                    + e.getLocalizedMessage();
            System.out.println(text);
            e.printStackTrace();
        }
        return connection;
    }
}
