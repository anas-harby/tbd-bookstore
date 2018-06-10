package com.tbdbookstore.core.control;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jdbc.DBException;
import com.tbdbookstore.core.pojo.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            User user = Main.getDBConnector().getUserInfo();
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            email.setText(user.getEmail());
            telephoneNumber.setText(user.getPhoneNumber());
            address.setText(user.getShippingAddress());
            attachPasswordValidator();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void attachPasswordValidator() {
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
}
