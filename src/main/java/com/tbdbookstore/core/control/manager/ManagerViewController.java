package com.tbdbookstore.core.control.manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerViewController implements Initializable {
    @FXML private StackPane root;
    @FXML private BorderPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void switchToHomeView(MouseEvent mouseEvent) {
        try {
          mainPane.getChildren().remove(mainPane.getCenter());
        } catch (Exception ignored) { }
        try {
            BorderPane homePane = FXMLLoader.load(getClass().getResource(
                    "/com/tbdbookstore/view/fxml/ManagerHome.fxml"));
            mainPane.setCenter(homePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToOrdersView(MouseEvent mouseEvent) {
        try {
            mainPane.getChildren().remove(mainPane.getCenter());
        } catch (Exception ignored) { }
        try {
            BorderPane ordersPane = FXMLLoader.load(getClass().getResource(
                    "/com/tbdbookstore/view/fxml/ManagerOrders.fxml"));
            mainPane.setCenter(ordersPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
