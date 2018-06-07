package com.tbdbookstore.core.uicontrols.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ManagerOrderCardControl extends VBox {
    public ManagerOrderCardControl() {
        loadFxml("/com/tbdbookstore/view/fxml/manager/ManagerOrderCard.fxml");
    }

    private void loadFxml(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                path));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
