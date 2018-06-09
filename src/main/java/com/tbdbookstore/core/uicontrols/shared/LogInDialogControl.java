package com.tbdbookstore.core.uicontrols.shared;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LogInDialogControl extends JFXDialog {

    @FXML
    private JFXDialog root;
    @FXML
    private Label heading;

    /* Dialog Inner Fields */
    @FXML
    private JFXTextField userNameField;
    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton acceptButton;
    @FXML
    private JFXButton cancelButton;

    public LogInDialogControl() {
        loadFxml("/com/tbdbookstore/view/fxml/main/LogInDialog.fxml");
        attachValidators();

        acceptButton.setOnMouseClicked(e -> root.close());
        cancelButton.setOnMouseClicked(e -> root.close());
    }

    private void attachValidators() {
            userNameField.focusedProperty().addListener((o, oldVal, newVal) -> {
                if (!newVal)
                    userNameField.validate();
            });
            passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
                if (!newVal)
                    passwordField.validate();
            });
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

    public void setOnAcceptClick(EventHandler<? super MouseEvent> eventHandler) {
        this.acceptButton.setOnMouseClicked(eventHandler);
    }

    public void setOnCancelClick(EventHandler<? super MouseEvent> eventHandler) {
        this.cancelButton.setOnMouseClicked(eventHandler);
    }

    public String getUserName() {
        return userNameField.getText();
    }
    public String getPassword(){
        return passwordField.getText();
    }
    public boolean hasErrors() {
            for (ValidatorBase v : userNameField.getValidators())
                if (v.getHasErrors())
                    return true;
            for (ValidatorBase v : passwordField.getValidators())
                if (v.getHasErrors())
                    return true;
        return false;
    }

    public void setHeading(String s) {
        this.heading.setText(s);
    }
}
