package com.tbdbookstore.core;

import com.tbdbookstore.core.jasperreports.Report;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/tbdbookstore/view/fxml/Primary.fxml"));
        Scene scene = new Scene(root, 1280, 800);
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();

        /*for jasper trial only*/ //TODO remove later
        Connection connection =  connectToDatabase("jdbc:mysql://localhost:3306/BOOKSTORE","root", "sara1201");
        Report report = new Report("/home/salma/IdeaProjects/tbd-bookstore/src/main/resources/com/tbdbookstore/view/jrxml/trial.jrxml",connection);
    }

    public static Connection connectToDatabase(String databaseName, String userName, String password) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(databaseName,userName,password);
        } catch(Exception e) {
            String text = "Could not connect to the database: " + e.getMessage() + " "
                    + e.getLocalizedMessage();
            System.out.println(text);
           e.printStackTrace();
        }
        return connection;
    }
}
