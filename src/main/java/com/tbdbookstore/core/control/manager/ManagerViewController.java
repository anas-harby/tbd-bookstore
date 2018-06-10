package com.tbdbookstore.core.control.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jdbc.Connector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ManagerViewController implements Initializable {
    @FXML
    private BorderPane mainPane;
    @FXML
    private JFXButton logOut;
    private Map<String, FXMLLoader> loadedFxmls;
    private JFXSnackbar snackbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadedFxmls = new HashMap<>();
        logOut.setOnMouseClicked(event -> {
            logOut("/com/tbdbookstore/view/fxml/main/HomePageView.fxml");

        });

    }

    public void logOut(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Pane pane = loader.load();
            Main.getRoot().getChildren().setAll(pane);
            Main.setLoader(loader);
            Main.setConnector(null);
        } catch (IOException ex) {

        }
    }

    private void switchView(String path) throws IOException {
        try {
            mainPane.getChildren().remove(mainPane.getCenter());
        } catch (Exception ignored) { }
        if (!loadedFxmls.containsKey(path)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            loader.load();
            loadedFxmls.put(path, loader);
        }
        mainPane.setCenter(loadedFxmls.get(path).getRoot());
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
