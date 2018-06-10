package com.tbdbookstore.core.control.user;

import com.gluonhq.charm.glisten.control.CardPane;
import com.jfoenix.controls.JFXButton;
import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.uicontrols.user.UserOrderCardControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class UserShoppingCartViewController implements Initializable {
    @FXML private Label subtotalPrice;
    @FXML private Label totalPrice;
    @FXML private Label shippingPrice;
    @FXML private CardPane cardPane;
    @FXML private JFXButton prevButton;
    @FXML private JFXButton nextButton;
    private double subtotal = 0;
    private double shipping = 0;
    private int offset = 0;
    private HashMap<String, Book> orderedBooks;
    private static final int PAGE_COUNT = 5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderedBooks = new HashMap<>();
        prevButton.setDisable(true);
        nextButton.setDisable(true);
        updateTotal();
    }

    public void addOrder(Book book) {
        if (orderedBooks.containsKey(book.getISBN())) {
            //TODO: check if a book card is existing in the current view and edit it.
            return;
        }
        orderedBooks.put(book.getISBN(), book);
        subtotal += book.getSellingPrice() * book.getStockQuantity();
        shipping = 5;
        updateTotal();

        if (cardPane.getCards().size() < PAGE_COUNT) {
            UserOrderCardControl card = getNewCard(book);
            cardPane.getCards().add(card);
        } else if (cardPane.getCards().size() == PAGE_COUNT)
            nextButton.setDisable(false);
    }

    private void updateTotal() {
        shippingPrice.setText(Double.toString(shipping) + "$");
        subtotalPrice.setText(Double.toString(subtotal) + "$");
        totalPrice.setText(Double.toString(subtotal + shipping) + "$");
    }

    public void getPrevPage(MouseEvent mouseEvent) {
        offset -= PAGE_COUNT + cardPane.getCards().size();
        if (offset == 0) {
            prevButton.setDisable(true);
            nextButton.setDisable(false);
        }
        cardPane.getCards().clear();
        List<Book> books = getBookPage();
        for (Book book : books)
            cardPane.getCards().add(getNewCard(book));
    }

    public void getNextPage(MouseEvent mouseEvent) {
        if (offset == PAGE_COUNT)
            prevButton.setDisable(false);
        cardPane.getCards().clear();
        List<Book> books = getBookPage();
        for (Book book : books)
            cardPane.getCards().add(getNewCard(book));
        if (books.size() < PAGE_COUNT)
            nextButton.setDisable(true);
    }

    private List<Book> getBookPage() {
        List<Book> books = new ArrayList<>();
        List<String> keySet = new ArrayList<>(orderedBooks.keySet());
        int oldOffset = offset;

        for (int i = oldOffset; i < oldOffset + PAGE_COUNT && i < orderedBooks.size(); i++) {
            offset++;
            books.add(orderedBooks.get(keySet.get(i)));
        }

        return books;
    }

    private UserOrderCardControl getNewCard(Book book) {
        UserOrderCardControl card = new UserOrderCardControl(book);

        card.setOnDeleteButtonClick(mouseEvent -> {
            List<String> keySet = new ArrayList<>(orderedBooks.keySet());
            int indDeleted = keySet.indexOf(book.getISBN());
            orderedBooks.remove(book.getISBN());
            subtotal -= book.getSellingPrice() * book.getStockQuantity();
            cardPane.getCards().remove(card);
            int indToAdd = indDeleted <= offset ? offset : indDeleted;
            if (indToAdd < orderedBooks.size())
                cardPane.getCards().add(getNewCard(orderedBooks.get(keySet.get(indToAdd))));
            else
                nextButton.setDisable(true);

            if (cardPane.getCards().isEmpty())
                shipping = 0;
            updateTotal();
        });

        return card;
    }

}
