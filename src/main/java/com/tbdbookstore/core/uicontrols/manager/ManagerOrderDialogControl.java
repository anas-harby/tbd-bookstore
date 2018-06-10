package com.tbdbookstore.core.uicontrols.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import com.tbdbookstore.core.pojo.Order;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerOrderDialogControl extends JFXDialog {

    @FXML
    private JFXDialog root;
    @FXML
    private Label heading;

    /* Dialog Inner Fields */
    @FXML
    private JFXTextField bookISBNField;
    @FXML
    private JFXTextField quantityField;

    @FXML
    private JFXButton acceptButton;
    @FXML
    private JFXButton cancelButton;

    private List<JFXTextField> dialogFields;

    public ManagerOrderDialogControl() {
        loadFxml("/com/tbdbookstore/view/fxml/manager/ManagerOrdersDialog.fxml");
        dialogFields = new ArrayList<>(Arrays.asList(bookISBNField,quantityField));
        attachValidators();

        acceptButton.setOnMouseClicked(e -> root.close());
        cancelButton.setOnMouseClicked(e -> root.close());
    }

    private void attachValidators() {
        for (JFXTextField tf : dialogFields)
            tf.focusedProperty().addListener((o, oldVal, newVal) -> {
                if (!newVal)
                    tf.validate();
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

    public Order getValue() {
        Order order = new Order(bookISBNField.getText(),Integer.parseInt(quantityField.getText()));
        return order;
    }
    public boolean hasErrors() {
        for (JFXTextField tf : dialogFields)
            for (ValidatorBase v : tf.getValidators())
                if (v.getHasErrors())
                    return true;
        return false;
    }

    public void setHeading(String s) {
        this.heading.setText(s);
    }
}
