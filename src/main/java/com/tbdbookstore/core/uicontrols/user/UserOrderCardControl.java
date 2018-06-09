package com.tbdbookstore.core.uicontrols.user;

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

public class UserOrderCardControl extends VBox {

    /* Model properties. */
    @FXML
    private ImageView coverimg;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label isbn;
    @FXML private Label genre;
    @FXML private Label publisher;
    @FXML private Label year;
    @FXML private Label price;
    @FXML private Button editButton;
    @FXML private Button deleteButton;

    public UserOrderCardControl() {
        loadFxml("/com/tbdbookstore/view/fxml/user/UserOrderCard.fxml");
    }

    public UserOrderCardControl(Book book) {
        loadFxml("/com/tbdbookstore/view/fxml/user/UserOrderCard.fxml");
        isbn.setText(book.getISBN());
        title.setText(book.getTitle());
        author.setText(book.getAuthors().get(0));
        publisher.setText(book.getPublisher());
        year.setText(book.getPublicationYear());
        genre.setText(book.getGenre());
        price.setText(Double.toString(book.getSellingPrice()));
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

    private void logAll() {
        System.out.println("Title: " + title.getText());
        System.out.println("Author: " + author.getText());
    }

    public void setISBN(String isbn) {
        if (isbn.matches("[a-zA-Z]+"))
            throw new RuntimeException("ISBN can not have an alphabetic character.");
        this.isbn.setText(isbn);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setAuthor(String author) {
        this.author.setText(author);
    }

    public void setGenre(String genre) {
        this.genre.setText(genre);
    }

    public void setPublisher(String publisher) {
        this.publisher.setText(publisher);
    }

    public void setOnEditButtonClick(EventHandler<? super MouseEvent> eventHandler) {
        this.editButton.setOnMouseClicked(eventHandler);
    }

    public void setOnDeleteButtonClick(EventHandler<? super MouseEvent> eventHandler) {
        this.deleteButton.setOnMouseClicked(eventHandler);
    }
}
