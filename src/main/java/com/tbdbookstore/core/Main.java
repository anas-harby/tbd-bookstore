package com.tbdbookstore.core;

import com.tbdbookstore.core.jdbc.Connector;
import com.tbdbookstore.core.jdbc.JDBCController;
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
    }git

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

        //connector = JDBCController.logIn("new1", "new");

        /*for jasper trial only*/ //TODO remove later
//        Connection connection = connectToDatabase("jdbc:mysql://localhost:3306/BOOKSTORE", "root", "sara1201");
//
//        File file = new File("./src/main/resources/com/tbdbookstore/view/jrxml/trial.jrxml");
//        Report report = new Report(file.getAbsolutePath(), connection);
    }

//    public static Connection connectToDatabase(String databaseName, String userName, String password) {
//        Connection connection = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection(databaseName, userName, password);
//        } catch (Exception e) {
//            String text = "Could not connect to the database: " + e.getMessage() + " "
//                    + e.getLocalizedMessage();
//            System.out.println(text);
//            e.printStackTrace();
//        }
//        return connection;
//    }
}
