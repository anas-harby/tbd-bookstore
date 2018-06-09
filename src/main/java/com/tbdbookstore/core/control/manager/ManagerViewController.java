package com.tbdbookstore.core.control.manager;

import com.jfoenix.controls.JFXSnackbar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerViewController implements Initializable {
    @FXML private BorderPane mainPane;
    private JFXSnackbar snackbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void switchView(String path) throws IOException {
        try {
            mainPane.getChildren().remove(mainPane.getCenter());
        } catch (Exception ignored) { }
        Pane homePane = FXMLLoader.load(getClass().getResource(
                path));
        mainPane.setCenter(homePane);
    }

    public void switchToHomeView(MouseEvent mouseEvent) {
        try {
            switchView("/com/tbdbookstore/view/fxml/manager/ManagerHomeView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToOrdersView(MouseEvent mouseEvent) {
        try {
            switchView("/com/tbdbookstore/view/fxml/manager/ManagerOrdersView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToOProfileView(MouseEvent mouseEvent) {
        try {
            switchView("/com/tbdbookstore/view/fxml/shared/Profile.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToAdministrationView(MouseEvent mouseEvent) {
        try {
            switchView("/com/tbdbookstore/view/fxml/manager/Administration.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
