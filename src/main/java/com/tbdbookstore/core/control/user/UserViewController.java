package com.tbdbookstore.core.control.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class UserViewController implements Initializable {
    @FXML private BorderPane mainPane;
    private Map<String, Pane> loadedFxmls;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadedFxmls = new HashMap<>();
    }

    private void switchView(String path) throws IOException {
        try {
            mainPane.getChildren().remove(mainPane.getCenter());
        } catch (Exception ignored) { }
        Pane homePane = loadedFxmls.containsKey(path) ? loadedFxmls.get(path) : FXMLLoader.load(getClass().getResource(
                path));
        mainPane.setCenter(homePane);
        loadedFxmls.put(path, homePane);
    }

    public void switchToHomeView(MouseEvent mouseEvent) {
        try {
            switchView("/com/tbdbookstore/view/fxml/user/UserHomeView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToShoppingCartView(MouseEvent mouseEvent) {
        try {
            switchView("/com/tbdbookstore/view/fxml/user/UserShoppingCartView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToProfileView(MouseEvent mouseEvent) {
        try {
            switchView("/com/tbdbookstore/view/fxml/shared/Profile.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
