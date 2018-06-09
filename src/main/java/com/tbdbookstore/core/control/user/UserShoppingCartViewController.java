package com.tbdbookstore.core.control.user;

import com.gluonhq.charm.glisten.control.CardPane;
import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.uicontrols.user.UserOrderCardControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UserShoppingCartViewController implements Initializable {
    @FXML private CardPane cardPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void addOrder(Book book) {
        UserOrderCardControl card = new UserOrderCardControl(book);
        cardPane.getCards().add(card);
    }
}
