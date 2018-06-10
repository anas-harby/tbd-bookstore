package com.tbdbookstore.core.uicontrols.manager;

import com.tbdbookstore.core.pojo.Book;
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

    private double priceVal;

    public ManagerOrderCardControl() {
        loadFxml("/com/tbdbookstore/view/fxml/manager/ManagerOrderCard.fxml");
    }

    public ManagerOrderCardControl(Book book) {
        loadFxml("/com/tbdbookstore/view/fxml/manager/ManagerOrderCard.fxml");
        isbn.setText(book.getISBN());
        title.setText(book.getTitle());
        author.setText(book.getAuthors().get(0));
        publisher.setText(book.getPublisher());
        year.setText(book.getPublicationYear());
        genre.setText(book.getGenre());
        quantity.setText(Integer.toString(book.getStockQuantity()));
        price.setText(Double.toString(book.getSellingPrice()) + "$");
        priceVal = book.getSellingPrice();
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

}
