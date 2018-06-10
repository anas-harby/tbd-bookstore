package com.tbdbookstore.core.control.user;

import com.gluonhq.charm.glisten.control.CardPane;
import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.uicontrols.user.UserOrderCardControl;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class UserShoppingCartViewController implements Initializable {
    @FXML private Label subtotalPrice;
    @FXML private Label totalPrice;
    @FXML private Label shippingPrice;
    @FXML private CardPane cardPane;
    private double subtotal = 0;
    private double shipping = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTotal();
    }

    public void addOrder(Book book) {
        UserOrderCardControl card = new UserOrderCardControl(book);
        cardPane.getCards().add(card);
        subtotal += book.getSellingPrice() * book.getStockQuantity();
        shipping = 5;
        updateTotal();

        card.setOnDeleteButtonClick(mouseEvent -> {
            subtotal -= card.getPrice() * card.getQuantity();
            cardPane.getCards().remove(card);
            if (cardPane.getCards().isEmpty())
                shipping = 0;
            updateTotal();
        });
    }

    private void updateTotal() {
        shippingPrice.setText(Double.toString(shipping) + "$");
        subtotalPrice.setText(Double.toString(subtotal) + "$");
        totalPrice.setText(Double.toString(subtotal + shipping) + "$");
    }
}
