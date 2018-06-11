package com.tbdbookstore.core.uicontrols.manager;

import com.jfoenix.controls.JFXSnackbar;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jdbc.DBException;
import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.pojo.Order;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class ManagerOrderCardControl extends VBox {
    /* Model properties. */
    @FXML
    private ImageView coverimg;
    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label isbn;
    @FXML
    private Label genre;
    @FXML
    private Label publisher;
    @FXML
    private Label year;
    @FXML
    private Label price;
    @FXML
    private Label quantity;
    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;
    @FXML
    private Button confirmButton;

    @FXML
    private Label Quantity;
    @FXML
    private Label orderID;

    private double priceVal;

    public ManagerOrderCardControl() {
        loadFxml("/com/tbdbookstore/view/fxml/manager/ManagerOrderCard.fxml");
    }

    public ManagerOrderCardControl(Order order) {
        loadFxml("/com/tbdbookstore/view/fxml/manager/ManagerOrderCard.fxml");
        try {
            Book book = Main.getDBConnector().getOrderedBook(order.getISBN());
            isbn.setText(book.getISBN());
            title.setText(book.getTitle());
            author.setText(book.getAuthorsString());
            publisher.setText(book.getPublisher());
            year.setText(book.getPublicationYear());
            genre.setText(book.getGenre());
            price.setText(Double.toString(book.getSellingPrice()) + "$");
            priceVal = book.getSellingPrice();
            quantity.setText(Integer.toString(order.getQuantity()));
            orderID.setText(Integer.toString(order.getId()));
        } catch (DBException e) {

        }
    }

    private void loadFxml(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                path));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void setOnDeleteButtonClick(EventHandler<? super MouseEvent> eventHandler) {
        this.deleteButton.setOnMouseClicked(eventHandler);
    }

    public void setOnEditButtonClick(EventHandler<? super MouseEvent> eventHandler) {
        this.editButton.setOnMouseClicked(eventHandler);
    }

    public void setOnConfirmButtonClick(EventHandler<? super MouseEvent> eventHandler) {
        this.confirmButton.setOnMouseClicked(eventHandler);
    }

}
