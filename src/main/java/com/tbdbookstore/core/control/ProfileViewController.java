package com.tbdbookstore.core.control;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jdbc.DBException;
import com.tbdbookstore.core.pojo.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileViewController implements Initializable {
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXPasswordField confirmPassword;
    @FXML
    private JFXTextField telephoneNumber;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXBadge passwordMatch;
    @FXML
    private JFXBadge passwordMismatch;

    private JFXSnackbar bar;

    private List<JFXTextField> allFields;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bar = new JFXSnackbar(Main.getRoot());
        try {
            allFields = new ArrayList<>(Arrays.asList(firstName, lastName, email, telephoneNumber, address));
            user = Main.getDBConnector().getUserInfo();
            showInfo();
            attachValidators();
            attachPasswordMatching();
        } catch (DBException e) {
            bar.enqueue(new JFXSnackbar.SnackbarEvent(Main.getErrorMsg(e)));
        }
    }

    public void editUserInfo() {
        if (hasErrors())
            return; // TODO: feedback
        try {
            User user = this.user;
            user.setFirstName(firstName.getText());
            user.setLastName(lastName.getText());
            user.setEmail(email.getText());
            user.setPhoneNumber(telephoneNumber.getText());
            user.setShippingAddress(address.getText());
            if (!password.getText().equals("")) // Password changed
                user.setPassword(password.getText());
            Main.getDBConnector().editUserInfo(user);
            this.user = user;
            // TODO: feedback message
        } catch (DBException e) {
            bar.enqueue(new JFXSnackbar.SnackbarEvent(Main.getErrorMsg(e)));
        }
    }

    public void reset() {
        showInfo();
        password.setText("");
        confirmPassword.setText("");
    }

    private void showInfo() {
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        telephoneNumber.setText(user.getPhoneNumber());
        address.setText(user.getShippingAddress());
    }

    private void attachValidators() {
        for (JFXTextField textField : allFields)
            textField.focusedProperty().addListener((o, oldVal, newVal) -> {
                if (!newVal)
                    textField.validate();
            });
    }

    private void attachPasswordMatching() {
        confirmPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(password.getText())) {
                passwordMatch.setVisible(true);
                passwordMismatch.setVisible(false);
            } else {
                passwordMismatch.setVisible(true);
                passwordMatch.setVisible(false);
            }
        });
    }

    private boolean hasErrors() {
        if (!password.getText().equals(confirmPassword.getText()))
            return true;
        for (JFXTextField textField : allFields)
            for (ValidatorBase validatorBase : textField.getValidators())
                if (validatorBase.getHasErrors())
                    return true;
        return false;
    }
}
