package com.tbdbookstore.core.control;

import com.gluonhq.charm.glisten.control.CardPane;
import com.tbdbookstore.core.uicontrols.BookCardControl;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    @FXML
    private CardPane cardPane;
    @FXML
    private Label sortTitle;

    private void init () {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BookCardControl bookCard = new BookCardControl();
        cardPane.getCards().add(bookCard);
        cardPane.getCards().add(new BookCardControl());
        cardPane.getCards().add(new BookCardControl());
        bookCard.setOnCartButtonClick(mouseEvent -> bookCard.setISBN("1111"));
    }
}
