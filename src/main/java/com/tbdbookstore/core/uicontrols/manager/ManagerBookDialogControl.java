package com.tbdbookstore.core.uicontrols.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jdbc.DBException;
import com.tbdbookstore.core.pojo.Book;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerBookDialogControl extends JFXDialog {
    @FXML private JFXDialog root;
    @FXML private Label heading;

    /* Dialog Inner Fields */
    @FXML private JFXTextField isbnField;
    @FXML private JFXTextField titleField;
    @FXML private JFXTextField authorsField;
    @FXML private JFXTextField publisherField;
    @FXML private JFXTextField yearField;
    @FXML private JFXTextField priceField;
    @FXML private JFXTextField stockQuantityField;
    @FXML private JFXTextField minQuantityField;
    @FXML private JFXComboBox genreField;

    @FXML private JFXButton acceptButton;
    @FXML private JFXButton cancelButton;

    private List<JFXTextField> dialogFields;

    public ManagerBookDialogControl() {
        loadFxml("/com/tbdbookstore/view/fxml/manager/ManagerBookDialog.fxml");
        dialogFields = new ArrayList<>(Arrays.asList(isbnField, titleField, authorsField,
                publisherField, yearField, priceField, stockQuantityField, minQuantityField));
        loadGenres();
        attachValidators();
        acceptButton.setOnMouseClicked(e -> root.close());
        cancelButton.setOnMouseClicked(e -> root.close());
    }

    public void setOnAcceptClick(EventHandler<? super MouseEvent> eventHandler) {
        this.acceptButton.setOnMouseClicked(eventHandler);
    }

    public void setOnCancelClick(EventHandler<? super MouseEvent> eventHandler) {
        this.cancelButton.setOnMouseClicked(eventHandler);
    }

    public Book getValue() {
        Book book = new Book(isbnField.getText());
        book.setTitle(titleField.getText());
        book.setAuthors(getAuthors());
        book.setGenre((String) genreField.getValue());
        book.setPublisher(publisherField.getText());
        book.setPublicationYear(yearField.getText());
        book.setSellingPrice(Integer.parseInt(priceField.getText()));
        book.setMinQuantity(Integer.parseInt(minQuantityField.getText()));
        book.setStockQuantity(Integer.parseInt(stockQuantityField.getText()));
        return book;
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

    public void clearFields() {
        for (JFXTextField jfxTextField : dialogFields) {
            jfxTextField.setText("");
            jfxTextField.resetValidation();
        }
        genreField.valueProperty().set(null);
    }

    private void loadGenres() {
        try {
            List<String> genres = Main.getDBConnector().getGenres();
            for (String genre : genres)
                genreField.getItems().add(genre);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void attachValidators() {
        for (JFXTextField tf : dialogFields) {
            tf.focusedProperty().addListener((o, oldVal, newVal) -> {
                if (!newVal)
                    tf.validate();
            });
        }
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

    private List<String> getAuthors() {
        List<String> authors = Arrays.asList(authorsField.getText().split(","));
        return authors.stream().map(String::trim).collect(Collectors.toList());
    }

    public void validate() {
        for (JFXTextField tf : dialogFields)
            tf.validate();
    }
}
