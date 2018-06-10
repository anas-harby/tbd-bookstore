package com.tbdbookstore.core.control.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jasperreports.Report;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerAdministrationController implements Initializable {

    @FXML
    private JFXTextField userNameField;
    @FXML
    private JFXButton jasperButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        jasperButton.setOnMouseClicked(event -> {
            File file = new File("./src/main/resources/com/tbdbookstore/view/jrxml/trial.jrxml");
//            Report report = new Report(file.getAbsolutePath(), Main.getDBConnector());
//            report.viewReport();
        });

    }
}
