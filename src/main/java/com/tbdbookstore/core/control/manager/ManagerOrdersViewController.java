package com.tbdbookstore.core.control.manager;

import com.gluonhq.charm.glisten.control.CardPane;
import com.tbdbookstore.core.uicontrols.OrderController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerOrdersViewController implements Initializable {
    @FXML
    private CardPane cardPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardPane.getCards().add(new OrderController());
    }

    public void acceptBookDialog(MouseEvent mouseEvent) {
    }

    public void cancelBookDialog(MouseEvent mouseEvent) {
    }
}
