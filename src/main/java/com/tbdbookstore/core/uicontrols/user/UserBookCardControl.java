package com.tbdbookstore.core.uicontrols.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.base.ValidatorBase;
import com.tbdbookstore.core.pojo.Book;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class UserBookCardControl extends VBox {
    /* Model properties. */
    @FXML private ImageView coverimg;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label isbn;
    @FXML private Label genre;
    @FXML private Label publisher;
    @FXML private Label year;
    @FXML private JFXButton cartButton;
    @FXML private JFXDialog dialog;
    private JFXPopup popup;
    private JFXButton checkPopup;
    private JFXTextField quantityField;

    private int quantity;

    public UserBookCardControl() {
        loadFxml("/com/tbdbookstore/view/fxml/user/UserBookCard.fxml");
        setupPopup();
    }

    public UserBookCardControl(Book book) {
        loadFxml("/com/tbdbookstore/view/fxml/user/UserBookCard.fxml");
        isbn.setText(book.getISBN());
        title.setText(book.getTitle());
        author.setText(book.getAuthorsString());
        publisher.setText(book.getPublisher());
        year.setText(book.getPublicationYear());
        genre.setText(book.getGenre());
        quantity = book.getStockQuantity();
        cartButton.setText(Double.toString(book.getSellingPrice()) + " (" + quantity + ")");
        setupPopup();
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

    public void setISBN(String isbn) {
        if (isbn.matches("[a-zA-Z]+"))
            throw new RuntimeException("BOOK_ISBN can not have an alphabetic character.");
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

    public void setOnCheckClick(EventHandler<? super MouseEvent> eventHandler) {
        this.checkPopup.setOnMouseClicked(eventHandler);
    }

    private void setupPopup() {
        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setStyle("-fx-background-color: #E0E0E0;");
        quantityField = new JFXTextField("1");
        quantityField.setMinWidth(20);
        quantityField.setPrefWidth(20);
        quantityField.setMaxWidth(20);
        quantityField.setStyle("-fx-background-color: #00000000;");
        checkPopup = new JFXButton("");
        quantityField.getValidators().add(new ValidatorBase() {
            @Override
            protected void eval() {
                if (srcControl.get() instanceof TextInputControl)
                    evalTextInputField();
            }
            private void evalTextInputField() {
                TextInputControl textField = (TextInputControl) srcControl.get();
                String text = textField.getText();
                try {
                    hasErrors.set(false);
                    if (!text.isEmpty()) {
                        int x = Integer.parseInt(text);
                        if (x > quantity)
                            hasErrors.set(true);
                    }
                } catch (Exception e) {
                    hasErrors.set(true);
                }
            }
        });
        quantityField.textProperty().addListener((o, oldVal, newVal) -> {
            quantityField.validate();
            if (quantityField.getValidators().get(0).getHasErrors())
                checkPopup.setDisable(true);
            else
                checkPopup.setDisable(false);
        });
        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.setGlyphName("CHECK");
        icon.setStyle("-fx-fill: #757575;");
        checkPopup.setGraphic(icon);
        hbox.getChildren().addAll(quantityField, checkPopup);
        popup = new JFXPopup(hbox);

        cartButton.setOnMouseClicked(e -> popup.show(cartButton, null, null, 20,
                cartButton.getHeight()));
    }

    public void hidePopup() {
        popup.hide();
    }

    public int getQuantity() {
        return Integer.parseInt(quantityField.getText());
    }
}
