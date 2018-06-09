package com.tbdbookstore.core.uicontrols.shared;

import com.jfoenix.controls.*;
import com.jfoenix.validation.base.ValidatorBase;
import com.sun.deploy.panel.TextFieldProperty;
import com.tbdbookstore.core.pojo.User;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpDialogControl extends JFXDialog {

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
    private JFXPasswordField confirmPasswordField;
    @FXML
    private JFXBadge wrongPassword;
    @FXML
    private JFXBadge correctPassword;
    @FXML
    private JFXTextField firstNameField;
    @FXML
    private JFXTextField lastNameField;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXTextField telNoField;
    @FXML
    private JFXTextField shippingAddrField;

    @FXML
    private JFXButton acceptButton;
    @FXML
    private JFXButton cancelButton;

    private List<JFXTextField> dialogFields;
    private List<JFXPasswordField> passwordFields;

    public SignUpDialogControl() {
        loadFxml("/com/tbdbookstore/view/fxml/main/SignUpDialog.fxml");
        dialogFields = new ArrayList<>(Arrays.asList(userNameField,
                firstNameField, lastNameField, emailField, telNoField, shippingAddrField));
        passwordFields = new ArrayList<>(Arrays.asList(passwordField, confirmPasswordField));
        attachValidators();
        confirmPasswordCheck();
        acceptButton.setOnMouseClicked(e -> root.close());
        cancelButton.setOnMouseClicked(e -> root.close());
    }

    private void attachValidators() {
        for (JFXTextField tf : dialogFields)
            tf.focusedProperty().addListener((o, oldVal, newVal) -> {
                if (!newVal)
                    tf.validate();
            });
        for (JFXPasswordField pf : passwordFields)
            pf.focusedProperty().addListener((o, oldVal, newVal) -> {
                if (!newVal)
                    pf.validate();
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

    public void confirmPasswordCheck() {
        this.confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(passwordField.getText())) {
                correctPassword.setVisible(true);
                wrongPassword.setVisible(false);
            } else {
                correctPassword.setVisible(false);
                wrongPassword.setVisible(true);
            }
        });
    }

    public User getValue() {
        User user = new User(userNameField.getText());
        user.setPassword(passwordField.getText());
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setEmail(emailField.getText());
        user.setPhoneNumber(telNoField.getText());
        user.setShippingAddress(shippingAddrField.getText());
        return user;
    }

    public boolean hasErrors() {
        for (JFXTextField tf : dialogFields)
            for (ValidatorBase v : tf.getValidators())
                if (v.getHasErrors())
                    return true;
        for (JFXPasswordField pf : passwordFields)
            for (ValidatorBase v : pf.getValidators())
                if (v.getHasErrors())
                    return true;
        return false;
    }

    public void setHeading(String s) {
        this.heading.setText(s);
    }
}
