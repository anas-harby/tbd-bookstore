package com.tbdbookstore.core.control.manager;

import com.gluonhq.charm.glisten.control.CardPane;
import com.jfoenix.controls.JFXButton;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jdbc.DBException;
import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.pojo.Order;
import com.tbdbookstore.core.uicontrols.manager.ManagerOrderCardControl;
import com.tbdbookstore.core.uicontrols.manager.ManagerOrderDialogControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerOrdersViewController implements Initializable {
    private static final int PAGE_COUNT = 5;
    private int offset = 0;
    private HashMap<String, Book> orderedBooks;

    private ManagerOrderDialogControl managerOrderDialogControl;

    @FXML private CardPane cardPane;
    @FXML private JFXButton prevButton;
    @FXML JFXButton nextButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        orderedBooks = new HashMap<>();
        managerOrderDialogControl = new ManagerOrderDialogControl();
        showOrders();
        managerOrderDialogControl.setOnAcceptClick(e -> {
            if (managerOrderDialogControl.hasErrors())
                return;
            Order order = managerOrderDialogControl.getValue();
            try {
                Main.getDBConnector().placeOrder(order);
                managerOrderDialogControl.close();
                addOrder(Main.getDBConnector().getOrderedBook(order.getISBN()));
            } catch (DBException ex) {
                //TODO HANDLE DB EXCEPTION
            }

        });
    }

    public void orderBooks(MouseEvent mouseEvent) {
        managerOrderDialogControl.show(Main.getRoot());
    }

    public void showOrders() {
        try {
            List<Order> orders = Main.getDBConnector().getOrders();
            for (Order order : orders) {
                Book book = Main.getDBConnector().getOrderedBook(order.getISBN());
                addOrder(book);
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
    public void getPrevPage(MouseEvent mouseEvent) {
        offset -= PAGE_COUNT;
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
        offset += PAGE_COUNT;
        if (offset == PAGE_COUNT)
            prevButton.setDisable(false);
        cardPane.getCards().clear();
        List<Book> books = getBookPage();
        for (Book book : books)
            cardPane.getCards().add(getNewCard(book));
        if (offset + cardPane.getCards().size() == orderedBooks.size())
            nextButton.setDisable(true);
    }

    private List<Book> getBookPage() {
        List<Book> books = new ArrayList<>();
        List<String> keySet = new ArrayList<>(orderedBooks.keySet());

        for (int i = offset; i < offset + PAGE_COUNT && i < orderedBooks.size(); i++)
            books.add(orderedBooks.get(keySet.get(i)));

        return books;
    }

    private ManagerOrderCardControl getNewCard(Book book) {
        ManagerOrderCardControl card = new ManagerOrderCardControl(book);

        card.setOnDeleteButtonClick(mouseEvent -> {
            orderedBooks.remove(book.getISBN());
            cardPane.getCards().remove(card);

            int indToAdd = offset + PAGE_COUNT - 1;
            if (indToAdd < orderedBooks.size())
                cardPane.getCards().add(getNewCard(orderedBooks.get(
                        new ArrayList<>(orderedBooks.keySet()).get(indToAdd))));
            else
                nextButton.setDisable(true);
        });

        return card;
    }

    public void addOrder(Book book) {
        if (orderedBooks.containsKey(book.getISBN())) {
            //TODO: check if a book card is existing in the current view and edit it.
            return;
        }
        orderedBooks.put(book.getISBN(), book);
        if (cardPane.getCards().size() < PAGE_COUNT) {
            ManagerOrderCardControl card = getNewCard(book);
            cardPane.getCards().add(card);
        } else if (cardPane.getCards().size() == PAGE_COUNT)
            nextButton.setDisable(false);
    }

}
