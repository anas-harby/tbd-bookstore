package com.tbdbookstore.core.control;

import com.jfoenix.controls.JFXSnackbar;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jdbc.Connector;
import com.tbdbookstore.core.jdbc.DBException;
import com.tbdbookstore.core.jdbc.JDBCController;
import com.tbdbookstore.core.pojo.User;
import com.tbdbookstore.core.uicontrols.manager.ManagerModesDialogControl;
import com.tbdbookstore.core.uicontrols.manager.ManagerOrderCardControl;
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
    private ManagerModesDialogControl managerModesDialogControl;
    private Connector connector;

    @FXML
    StackPane HomePane;
    private JFXSnackbar bar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpDialogControl = new SignUpDialogControl();
        logInDialogControl = new LogInDialogControl();
        managerModesDialogControl = new ManagerModesDialogControl();
         bar = new JFXSnackbar(HomePane);
        signUpDialogControl.setOnAcceptClick(e -> {
            if (signUpDialogControl.hasErrors())
                return;
            User newUser = signUpDialogControl.getValue();
            try {
                connector = JDBCController.signUp(newUser);
                Main.setConnector(connector);
                signUpDialogControl.close();
                //switch to user view
                switchView("/com/tbdbookstore/view/fxml/user/User.fxml");
            } catch (DBException ex) {
                bar.enqueue(new JFXSnackbar.SnackbarEvent(Main.getErrorMsg(ex)));
            }

        });
        logInDialogControl.setOnAcceptClick(e -> {
            if (logInDialogControl.hasErrors())
                return;
            String userName = logInDialogControl.getUserName();
            String password = logInDialogControl.getPassword();
            try {
                connector = JDBCController.logIn(userName, password);
                Main.setConnector(connector);
                User user = connector.getUserInfo();
                if (user.getRole().equals("manager")) {
                    logInDialogControl.close();
                    managerModesDialogControl.show(Main.getRoot());
                } else if (user.getRole().equals("user")) {
                    switchView("/com/tbdbookstore/view/fxml/user/User.fxml");
                }
            } catch (DBException ex) {
               bar.enqueue(new JFXSnackbar.SnackbarEvent(Main.getErrorMsg(ex)));
            }
        });

        logInDialogControl.setSignupLinkClick(event -> {
            logInDialogControl.close();
            signUp(null);
        });
        signUpDialogControl.setloginLinkClick(event -> {
            signUpDialogControl.close();
            LogIn(null);
        });
        managerModesDialogControl.managerModeClick(event -> {
            switchView("/com/tbdbookstore/view/fxml/manager/Manager.fxml");
        });
        managerModesDialogControl.userModeClick(event -> {
            switchView("/com/tbdbookstore/view/fxml/user/User.fxml");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Pane pane = loader.load();
            HomePane.getChildren().setAll(pane);
            Main.setLoader(loader);
        } catch (IOException ex) {

        }
    }

}
