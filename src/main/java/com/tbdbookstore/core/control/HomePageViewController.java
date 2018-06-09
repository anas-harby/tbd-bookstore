package com.tbdbookstore.core.control;

import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.pojo.User;
import com.tbdbookstore.core.uicontrols.shared.LogInDialogControl;
import com.tbdbookstore.core.uicontrols.shared.SignUpDialogControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageViewController implements Initializable {

    private SignUpDialogControl signUpDialogControl;
    private LogInDialogControl logInDialogControl;

    @FXML
    StackPane HomePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpDialogControl = new SignUpDialogControl();
        logInDialogControl = new LogInDialogControl();
        signUpDialogControl.setOnAcceptClick(e -> {
            if (signUpDialogControl.hasErrors())
                return;
            User newUser = signUpDialogControl.getValue();
            //TODO send new user object to jdbc
            signUpDialogControl.close();
            //switch to user view
            switchView("/com/tbdbookstore/view/fxml/user/User.fxml");

        });
        logInDialogControl.setOnAcceptClick(e -> {
            if (logInDialogControl.hasErrors())
                return;
            String userName = logInDialogControl.getUserName();
            String password = logInDialogControl.getPassword();
            //TODO send log in data to jdbc and check role to load certain view


        });
    }

    public void signUp(MouseEvent mouseEvent) {
        signUpDialogControl.show(Main.getRoot());
    }

    public void LogIn(MouseEvent mouseEvent) {
        logInDialogControl.show(Main.getRoot());
    }

    public void switchView(String path) {
        try {
            Pane pane = FXMLLoader.load(getClass().getResource(path));
            HomePane.getChildren().setAll(pane);
        } catch (IOException ex) {

        }
    }

}
