package com.tbdbookstore.core.control.user;

import com.tbdbookstore.core.uicontrols.user.UserBookTableControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {
    @FXML private UserBookTableControl userTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void click(MouseEvent event) {
        System.out.println("CLICK");
        userTable.generate(10);
    }
}
