package com.tbdbookstore.core.control.manager;

import com.gluonhq.charm.glisten.control.CardPane;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.uicontrols.manager.ManagerBookCardControl;
import com.tbdbookstore.core.uicontrols.manager.ManagerBookDialogControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerHomeViewController implements Initializable {
    @FXML private CardPane cardPane;
    private ManagerBookDialogControl dialogControl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ManagerBookCardControl bookCard = new ManagerBookCardControl();
        cardPane.getCards().add(bookCard);

        dialogControl = new ManagerBookDialogControl();
        dialogControl.setOnAcceptClick(e -> {
            if (dialogControl.hasErros())
                return;
            Book newBook = dialogControl.getValue();
            System.out.println(newBook);
        });
    }

    public void addBook(MouseEvent mouseEvent) {
        dialogControl.showDialog(Main.getRoot());
    }
}
