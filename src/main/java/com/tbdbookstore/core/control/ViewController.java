package com.tbdbookstore.core.control;

import com.gluonhq.charm.glisten.control.CardPane;
import com.tbdbookstore.core.uicontrols.BookCardControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    @FXML
    private CardPane cardPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BookCardControl bookCard = new BookCardControl();
        cardPane.getCards().add(bookCard);
        bookCard.setOnCartButtonClick(mouseEvent -> cardPane.getCards().add(new BookCardControl()));
    }
}
