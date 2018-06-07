package com.tbdbookstore.core.control.manager;

import com.jfoenix.controls.JFXSnackbar;
import com.tbdbookstore.core.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerViewController implements Initializable {
    @FXML private BorderPane mainPane;
    private JFXSnackbar snackbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void switchToHomeView(MouseEvent mouseEvent) {
        try {
          mainPane.getChildren().remove(mainPane.getCenter());
        } catch (Exception ignored) { }
        try {
            BorderPane homePane = FXMLLoader.load(getClass().getResource(
                    "/com/tbdbookstore/view/fxml/manager/ManagerHomeView.fxml"));
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
                    "/com/tbdbookstore/view/fxml/manager/ManagerOrdersView.fxml"));
            mainPane.setCenter(ordersPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToOProfileView(MouseEvent mouseEvent) {
        try {
            mainPane.getChildren().remove(mainPane.getCenter());
        } catch (Exception ignored) { }
        try {
            BorderPane profilePane = FXMLLoader.load(getClass().getResource(
                    "/com/tbdbookstore/view/fxml/shared/Profile.fxml"));
            mainPane.setCenter(profilePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToAdministrationView(MouseEvent mouseEvent) {
        try {
            mainPane.getChildren().remove(mainPane.getCenter());
        } catch (Exception ignored) { }
        try {
            GridPane administrationPane = FXMLLoader.load(getClass().getResource(
                    "/com/tbdbookstore/view/fxml/manager/Administration.fxml"));
            mainPane.setCenter(administrationPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
