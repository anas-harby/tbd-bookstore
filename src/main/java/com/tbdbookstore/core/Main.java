package com.tbdbookstore.core;

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
    public  static void setLoader(FXMLLoader fxmlLoader){
        loader = fxmlLoader;
    }
    public static void setConnector(Connector DBconnector){
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
}