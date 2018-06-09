package com.tbdbookstore.core.control.user;

import com.gluonhq.charm.glisten.control.CardPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.jdbc.DBException;
import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.uicontrols.user.UserBookCardControl;
import com.tbdbookstore.core.util.BookSearchProcessor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UserHomeViewController implements Initializable {
    @FXML private JFXButton searchButton;
    @FXML private JFXTextField searchArea;
    @FXML private ScrollPane scrollPane;
    @FXML private CardPane cardPane;
    @FXML public JFXButton prevButton;
    @FXML public JFXButton nextButton;
    private int offset = 0;
    private Book currSearchVal = null;

    private static final int PAGE_COUNT = 25;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search(null);
    }

    private HashMap<String, Book> getSearchResults() {
        try {
            return Main.getDBConnector().search(currSearchVal, null, offset * PAGE_COUNT, PAGE_COUNT);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private void resetView() {
        cardPane.getCards().clear();
        offset = 0;
        prevButton.setDisable(true);
        nextButton.setDisable(false);
    }

    public void search(MouseEvent mouseEvent) {
        resetView();
        currSearchVal = BookSearchProcessor.process(searchArea.getText());
        HashMap<String, Book> books = getSearchResults();
        for (Book book : books.values())
            cardPane.getCards().add(getNewCard(book));

        if (getCardinality(books.values()) < PAGE_COUNT)
            nextButton.setDisable(true);
    }

    public void getPrevPage(MouseEvent mouseEvent) {
        offset--;
        if (offset == 0)
            prevButton.setDisable(true);
        else
            nextButton.setDisable(false);
        cardPane.getCards().clear();
        HashMap<String, Book> books = getSearchResults();
        for (Book book : books.values())
            cardPane.getCards().add(getNewCard(book));
    }

    public void getNextPage(MouseEvent mouseEvent) {
        offset++;
        if (offset == 1)
            prevButton.setDisable(false);
        cardPane.getCards().clear();
        HashMap<String, Book> books = getSearchResults();
        for (Book book : books.values())
            cardPane.getCards().add(getNewCard(book));
        if (getCardinality(books.values()) < PAGE_COUNT)
            nextButton.setDisable(true);
    }

    private int getCardinality(final Collection<Book> books) {
        int ret = 0;
        for (Book book : books)
            ret += book.getAuthors().size();
        return ret;
    }

    public void searchAlt(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            search(null);
    }

    private UserBookCardControl getNewCard(Book book) {
        UserBookCardControl card = new UserBookCardControl(book);
        card.setOnCartButtonClick(e -> {
            UserViewController controller = Main.getMainController();
            controller.getShoppingCartController().addOrder(book);
        });
        return card;
    }
}
