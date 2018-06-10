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
    private HashMap<String, Order> orderedBooks;

    private ManagerOrderDialogControl managerOrderDialogControl;

    @FXML
    private CardPane cardPane;
    @FXML
    private JFXButton prevButton;
    @FXML
    private JFXButton nextButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        orderedBooks = new HashMap<>();
        prevButton.setDisable(true);
        nextButton.setDisable(true);
        managerOrderDialogControl = new ManagerOrderDialogControl();
        showOrders();
        managerOrderDialogControl.setOnAcceptClick(e -> {
            if (managerOrderDialogControl.hasErrors())
                return;
            Order order = managerOrderDialogControl.getValue();
            try {
                Main.getDBConnector().placeOrder(order);
                managerOrderDialogControl.close();
                addOrder(order);
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
                addOrder(order);
            }
        } catch (DBException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getPrevPage(MouseEvent mouseEvent) {
        offset -= PAGE_COUNT;
        if (offset == 0) {
            prevButton.setDisable(true);
            nextButton.setDisable(false);
        }
        cardPane.getCards().clear();
        List<Order> orders = getBookPage();
        for (Order order : orders)
            cardPane.getCards().add(getNewCard(order));
    }

    public void getNextPage(MouseEvent mouseEvent) {
        offset += PAGE_COUNT;
        if (offset == PAGE_COUNT)
            prevButton.setDisable(false);
        cardPane.getCards().clear();
        List<Order> orders = getBookPage();
        for (Order order : orders)
            cardPane.getCards().add(getNewCard(order));
        if (offset + cardPane.getCards().size() == orderedBooks.size())
            nextButton.setDisable(true);
    }

    private List<Order> getBookPage() {
        List<Order> orders = new ArrayList<>();
        List<String> keySet = new ArrayList<>(orderedBooks.keySet());

        for (int i = offset; i < offset + PAGE_COUNT && i < orderedBooks.size(); i++)
            orders.add(orderedBooks.get(keySet.get(i)));

        return orders;
    }

    private ManagerOrderCardControl getNewCard(Order order) {
        ManagerOrderCardControl card = new ManagerOrderCardControl(order);

        card.setOnDeleteButtonClick(mouseEvent -> {
            orderedBooks.remove(order.getISBN());
            cardPane.getCards().remove(card);

            int indToAdd = offset + PAGE_COUNT - 1;
            if (indToAdd < orderedBooks.size())
                cardPane.getCards().add(getNewCard(orderedBooks.get(
                        new ArrayList<>(orderedBooks.keySet()).get(indToAdd))));
            else
                nextButton.setDisable(true);
        });

        card.setOnConfirmButtonClick(event -> {
            try {
                Main.getDBConnector().confirmOrder(order.getId());
                orderedBooks.remove(order.getISBN());
                cardPane.getCards().remove(card);

                int indToAdd = offset + PAGE_COUNT - 1;
                if (indToAdd < orderedBooks.size())
                    cardPane.getCards().add(getNewCard(orderedBooks.get(
                            new ArrayList<>(orderedBooks.keySet()).get(indToAdd))));
                else
                    nextButton.setDisable(true);

            } catch (DBException e) {

            }
        });
        card.setOnEditButtonClick(event -> {

        });

        return card;
    }

    public void addOrder(Order order) {
        if (orderedBooks.containsKey(order.getISBN())) {
            //TODO: check if a book card is existing in the current view and edit it.
            return;
        }
        orderedBooks.put(order.getISBN(), order);
        if (cardPane.getCards().size() < PAGE_COUNT) {
            ManagerOrderCardControl card = getNewCard(order);
            cardPane.getCards().add(card);
        } else if (cardPane.getCards().size() == PAGE_COUNT)
            nextButton.setDisable(false);
    }

}
