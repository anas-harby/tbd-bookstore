package com.tbdbookstore.core;

import com.jfoenix.controls.JFXSnackbar;
import com.tbdbookstore.core.jdbc.Connector;
import com.tbdbookstore.core.jdbc.DBException;
import com.tbdbookstore.core.jdbc.JDBCController;
import com.tbdbookstore.core.pojo.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {
    private static Connector connector;
    private static FXMLLoader loader;

    public static void main(String[] args) {
        launch(args);
    }

    public static StackPane getRoot() {
        return loader.getRoot();
    }

    public static <T extends Initializable> T getMainController() {
        return loader.getController();
    }

    public static Connector getDBConnector() {
        return connector;
    }

    public static void setLoader(FXMLLoader fxmlLoader) {
        loader = fxmlLoader;
    }

    public static void setConnector(Connector DBconnector) {
        connector = DBconnector;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("/com/tbdbookstore/view/fxml/main/HomePageView.fxml"));
        loader.load();
        Scene scene = new Scene(loader.getRoot(), 1280, 800);
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static String getErrorMsg(DBException e) {
        switch (e.getError()) {
            case HEAVY_LOAD:
                return "Heavy Load, Too Many Connections! Try Again Later";
            case ACCESS_DENIED:
                return "User Unregistered or Incorrect Password !";
            case ALREADY_REGISTERED:
                return "User Already Registered! Try A Different UserName";
            case CONNECTION_FAILED:
                return "Connection Failed! Try Again Later";
            case INTERNAL_ISSUE:
                return "Internal Database Issues! Try Again Later ";
            case DATA_TOO_LONG:
                return "Data field Is Too Long! Try A Different One";
            case UNKNOWN_ITEM:
                return "Cannot Add or Update a row, a Foreign Key Constraint Fails!";
            case STOCK_LACK:
                return "Invalid Stock Quantity!";
            case UNSPECIFIED:
                return "Unspecified Error!";
            case DUPLICATE_ITEM:
                return "Duplicate Key Value!";
            case KEY_FIELD_MISSING:
                return "A Key Field Is Missing!";
            default:
                return "";

        }
    }
}