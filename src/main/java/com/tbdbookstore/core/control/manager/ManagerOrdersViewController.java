package com.tbdbookstore.core.control.manager;

import com.gluonhq.charm.glisten.control.CardPane;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jdbc.DBException;
import com.tbdbookstore.core.jdbc.JDBCController;
import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.pojo.Order;
import com.tbdbookstore.core.pojo.User;
import com.tbdbookstore.core.uicontrols.manager.ManagerOrderCardControl;
import com.tbdbookstore.core.uicontrols.manager.ManagerOrderDialogControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerOrdersViewController implements Initializable {

    private ManagerOrderDialogControl managerOrderDialogControl;

    @FXML
    private CardPane cardPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardPane.getCards().add(new ManagerOrderCardControl());
        managerOrderDialogControl = new ManagerOrderDialogControl();
        managerOrderDialogControl.setOnAcceptClick(e -> {
            if (managerOrderDialogControl.hasErrors())
                return;
            Order order = managerOrderDialogControl.getValue();
            try {
                Main.getDBConnector().placeOrder(order);
                managerOrderDialogControl.close();
                ManagerOrderCardControl card = new ManagerOrderCardControl();
                // ManagerOrderCardControl card = new ManagerOrderCardControl((getBook(order.getISBN())));
                cardPane.getCards().add(card);
            } catch (DBException ex) {
                //TODO HANDLE DB EXCEPTION
            }

        });
    }
//    Book getBook(String isbn){
//        Book book = Main.getDBConnector().search();
//        return book;
//    }
    public void orderBooks(MouseEvent mouseEvent) {
        managerOrderDialogControl.show(Main.getRoot());
    }
}
