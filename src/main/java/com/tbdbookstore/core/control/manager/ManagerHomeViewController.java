package com.tbdbookstore.core.control.manager;

import com.gluonhq.charm.glisten.control.CardPane;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jdbc.DBException;
import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.uicontrols.manager.ManagerBookDialogControl;
import com.tbdbookstore.core.uicontrols.user.UserBookCardControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ManagerHomeViewController implements Initializable {
    @FXML private CardPane cardPane;
    private ManagerBookDialogControl dialogControl;
    private int offset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBooks();

        dialogControl = new ManagerBookDialogControl();
        dialogControl.setOnAcceptClick(e -> {
            if (dialogControl.hasErrors())
                return;
            Book newBook = dialogControl.getValue();
            System.out.println(newBook);
            dialogControl.close();
        });
    }

    private void showBooks() {
        Book searchVal = new Book(null);
        try {
            HashMap<String, Book> books = Main.getDBConnector().search(searchVal, null, offset, 25);
            for (Book book : books.values()) {
                cardPane.getCards().add(new UserBookCardControl(book));
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public void addBook(MouseEvent mouseEvent) {
        dialogControl.show(Main.getRoot());
    }
}
