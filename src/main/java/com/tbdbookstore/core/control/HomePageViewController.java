package com.tbdbookstore.core.control;

import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.pojo.User;
import com.tbdbookstore.core.uicontrols.shared.LogInDialogControl;
import com.tbdbookstore.core.uicontrols.shared.SignUpDialogControl;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePageViewController implements Initializable {

    private SignUpDialogControl signUpDialogControl;
    private LogInDialogControl signInDialogControl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpDialogControl = new SignUpDialogControl();
        signInDialogControl = new LogInDialogControl();
        signUpDialogControl.setOnAcceptClick(e -> {
            if (signUpDialogControl.hasErrors())
                return;
            User newUser = signUpDialogControl.getValue();
            System.out.println(newUser);
            //TODO switch to MANAGER or USER VIEW
            signUpDialogControl.close();
        });
      //TODO ADD SAME ACCEPT ACTION to login
    }

    public void signUp(MouseEvent mouseEvent) {
        signUpDialogControl.show(Main.getRoot());
    }

    public void LogIn(MouseEvent mouseEvent) {
        signInDialogControl.show(Main.getRoot());
    }
}
