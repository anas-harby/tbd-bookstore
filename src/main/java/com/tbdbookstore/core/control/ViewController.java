package com.tbdbookstore.core.control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    @FXML private BookTableController userTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void click(MouseEvent event) {
        System.out.println("CLICK");
        userTable.generate(10);
    }
}
