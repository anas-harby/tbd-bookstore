package com.tbdbookstore.core.control.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {
    @FXML private BorderPane mainPane;
    private Map<String, FXMLLoader> loadedFxmls;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadedFxmls = new HashMap<>();
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

    public UserShoppingCartViewController getShoppingCartController() {
        String path = "/com/tbdbookstore/view/fxml/user/UserShoppingCartView.fxml";
        if (!loadedFxmls.containsKey(path)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                loader.load();
                loadedFxmls.put(path, loader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return loadedFxmls.get(path).getController();
    }
}
