package com.tbdbookstore.core.control.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jasperreports.JasperReport;
import com.tbdbookstore.core.jasperreports.Report;
import com.tbdbookstore.core.jdbc.DBException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerAdministrationController implements Initializable {

    @FXML
    private JFXTextField userNameField;
    @FXML
    private JFXComboBox exportCombobox;
    @FXML
    private JFXButton jasperButton;

    @FXML
    JFXButton promoteUser;
    private Report report;
    private File reportFile;
    private JFXSnackbar bar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportFile = new File("./src/main/resources/com/tbdbookstore/view/jrxml/trial.jrxml");
        bar = new JFXSnackbar(Main.getRoot());
        promoteUser.setOnMouseClicked(event -> {
            try {
                Main.getDBConnector().promoteUser(userNameField.getText());
                bar.enqueue(new JFXSnackbar.SnackbarEvent("User Promoted Successfully !"));
            } catch (DBException ex) {
                bar.enqueue(new JFXSnackbar.SnackbarEvent(Main.getErrorMsg(ex)));
            }
        });
        jasperButton.setOnMouseClicked(event -> {
            try {
                report = new Report(reportFile.getAbsolutePath(), Main.getDBConnector().getConnection());
                report.viewReport();
            } catch (DBException e) {
               bar.enqueue(new JFXSnackbar.SnackbarEvent(Main.getErrorMsg(e)));
            }
        });
        exportCombobox.setOnAction((e) -> {
            int selectionIndex = exportCombobox.getSelectionModel().getSelectedIndex();
            exportCombobox.getSelectionModel().selectFirst();
            export(selectionIndex);
        });
    }

    private void export(int typeIndex) {
        try {
            report = new Report(reportFile.getAbsolutePath(), Main.getDBConnector().getConnection());
            switch (typeIndex) {
                case 0:
                    report.exportReport(JasperReport.Format.PDF, "output");
                    break;
                case 1:
                    report.exportReport(JasperReport.Format.HTML, "output");
                    break;
                case 2:
                    report.exportReport(JasperReport.Format.XLS, "output");
                    break;
            }

        } catch (DBException exception) {
            bar.enqueue(new JFXSnackbar.SnackbarEvent(Main.getErrorMsg(exception)));
        }
    }
}
