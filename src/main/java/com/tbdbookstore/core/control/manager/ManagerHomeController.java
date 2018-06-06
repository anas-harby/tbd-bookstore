package com.tbdbookstore.core.control.manager;

import com.gluonhq.charm.glisten.control.CardPane;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.uicontrols.BookCardManagerControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerHomeController implements Initializable {
    @FXML private CardPane cardPane;
    @FXML private JFXDialog addBookDialog;

    /* Dialog Inner Fields */
    @FXML private JFXTextField isbnField;
    @FXML private JFXTextField titleField;
    @FXML private JFXTextField authorsField;
    @FXML private JFXTextField publisherField;
    @FXML private JFXTextField yearField;
    @FXML private JFXTextField priceField;
    @FXML private JFXTextField quantityField;

    private List<JFXTextField> dialogFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BookCardManagerControl bookCard = new BookCardManagerControl();
        cardPane.getCards().add(bookCard);

        dialogFields = new ArrayList<>(Arrays.asList(isbnField, titleField, authorsField,
                publisherField, yearField, priceField, quantityField));
        attachValidators();
    }

    private void attachValidators() {
        for (JFXTextField tf : dialogFields)
            tf.focusedProperty().addListener((o, oldVal, newVal) -> {
                if (!newVal)
                    tf.validate();
            });
    }

    public void addBook(MouseEvent mouseEvent) {
        addBookDialog.show(Main.getRoot());
    }

    public void acceptBookDialog(MouseEvent mouseEvent) {
        for (JFXTextField tf : dialogFields)
            for (ValidatorBase v : tf.getValidators())
                if (v.getHasErrors())
                    return;

        String isbn = isbnField.getText();
        String title = titleField.getText();
        String authors[] = authorsField.getText().split(",");
        String publisher = publisherField.getText();
        Integer year = Integer.parseInt(yearField.getText());
        Integer price = Integer.parseInt(priceField.getText());
        Integer quantity = Integer.parseInt(quantityField.getText());

        addBookDialog.close();
    }

    public void cancelBookDialog(MouseEvent mouseEvent) {
        addBookDialog.close();
    }
}
