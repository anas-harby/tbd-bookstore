package com.tbdbookstore.core.uicontrols.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ManagerModesDialogControl extends JFXDialog {
    @FXML private JFXButton userModeButton;
    @FXML private JFXButton managerModeButton;
    @FXML private JFXDialog root;

    public ManagerModesDialogControl() {
        loadFxml("/com/tbdbookstore/view/fxml/manager/ManagerModesDialog.fxml");

        userModeButton.setOnMouseClicked(e -> root.close());
        managerModeButton.setOnMouseClicked(e -> root.close());
    }

    private void loadFxml(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    public void userModeClick(EventHandler<? super MouseEvent> eventHandler) {
        this.userModeButton.setOnMouseClicked(eventHandler);
    }
    public void managerModeClick(EventHandler<? super MouseEvent> eventHandler) {
        this.managerModeButton.setOnMouseClicked(eventHandler);
    }
}
