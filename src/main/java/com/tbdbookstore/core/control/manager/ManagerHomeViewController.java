package com.tbdbookstore.core.control.manager;

import com.gluonhq.charm.glisten.control.CardPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.tbdbookstore.core.Main;
import com.tbdbookstore.core.control.user.UserViewController;
import com.tbdbookstore.core.jdbc.DBException;
import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.pojo.Ordering;
import com.tbdbookstore.core.shared.Attribute;
import com.tbdbookstore.core.shared.OrderMode;
import com.tbdbookstore.core.uicontrols.manager.ManagerBookDialogControl;
import com.tbdbookstore.core.uicontrols.user.UserBookCardControl;
import com.tbdbookstore.core.util.BookSearchProcessor;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

public class ManagerHomeViewController implements Initializable {
    @FXML private CardPane cardPane;
    @FXML private JFXTextField searchArea;
    @FXML private JFXButton prevButton;
    @FXML private JFXButton nextButton;
    @FXML private FontAwesomeIconView isbnIcon;
    @FXML private FontAwesomeIconView titleIcon;
    @FXML private FontAwesomeIconView priceIcon;

    private ManagerBookDialogControl dialogControl;
    private int offset = 0;
    private Book currSearchVal = null;
    private Ordering currentOrdering = null;
    private static final int PAGE_COUNT = 25;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dialogControl = new ManagerBookDialogControl();
        dialogControl.setOnAcceptClick(e -> {
            if (dialogControl.hasErrors())
                return;
            Book newBook = dialogControl.getValue();
            try {
                Main.getDBConnector().addNewBook(newBook);
                dialogControl.close();
            } catch (DBException e1) {
                e1.printStackTrace();
            }
        });

        search(null);
    }

    public void search(MouseEvent mouseEvent) {
        resetView();
        currSearchVal = BookSearchProcessor.process(searchArea.getText());
        LinkedHashMap<String, Book> books = getSearchResults();
        for (Book book : books.values())
            cardPane.getCards().add(getNewCard(book));

        if (getCardinality(books.values()) < PAGE_COUNT)
            nextButton.setDisable(true);
    }

    private LinkedHashMap<String, Book> getSearchResults() {
        try {
            return Main.getDBConnector().search(currSearchVal, currentOrdering, offset * PAGE_COUNT, PAGE_COUNT);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return new LinkedHashMap<>();
    }

    private void resetView() {
        cardPane.getCards().clear();
        offset = 0;
        prevButton.setDisable(true);
        nextButton.setDisable(false);
        isbnIcon.setGlyphName("SORT");
        titleIcon.setGlyphName("SORT");
        priceIcon.setGlyphName("SORT");
    }

    public void getPrevPage(MouseEvent mouseEvent) {
        offset--;
        if (offset == 0)
            prevButton.setDisable(true);
        else
            nextButton.setDisable(false);
        cardPane.getCards().clear();
        LinkedHashMap<String, Book> books = getSearchResults();
        for (Book book : books.values())
            cardPane.getCards().add(getNewCard(book));
    }

    public void getNextPage(MouseEvent mouseEvent) {
        offset++;
        if (offset == 1)
            prevButton.setDisable(false);
        cardPane.getCards().clear();
        LinkedHashMap<String, Book> books = getSearchResults();
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
        card.setOnCheckClick(e -> {
            Book shopped = book.clone();
            shopped.setStockQuantity(card.getQuantity());
            UserViewController controller = Main.getMainController();
            controller.getShoppingCartController().addOrder(shopped);
            card.hidePopup();
        });
        return card;
    }

    public void sortISBN(MouseEvent mouseEvent) {
        if (currentOrdering == null || currentOrdering.getAttribute() != Attribute.BOOK_ISBN)
            currentOrdering = new Ordering(Attribute.BOOK_ISBN, OrderMode.ASC);
        else
            currentOrdering.setMode(currentOrdering.getMode() == OrderMode.ASC ? OrderMode.DESC : OrderMode.ASC);
        search(null);
        isbnIcon.setGlyphName(currentOrdering.getMode() == OrderMode.ASC ? "SORT_ASC" : "SORT_DESC");
    }

    public void sortTitle(MouseEvent mouseEvent) {
        if (currentOrdering == null || currentOrdering.getAttribute() != Attribute.BOOK_TITLE)
            currentOrdering = new Ordering(Attribute.BOOK_TITLE, OrderMode.ASC);
        else
            currentOrdering.setMode(currentOrdering.getMode() == OrderMode.ASC ? OrderMode.DESC : OrderMode.ASC);
        search(null);
        titleIcon.setGlyphName(currentOrdering.getMode() == OrderMode.ASC ? "SORT_ASC" : "SORT_DESC");
    }

    public void sortPrice(MouseEvent mouseEvent) {
        if (currentOrdering == null || currentOrdering.getAttribute() != Attribute.SELLING_PRICE)
            currentOrdering = new Ordering(Attribute.SELLING_PRICE, OrderMode.ASC);
        else
            currentOrdering.setMode(currentOrdering.getMode() == OrderMode.ASC ? OrderMode.DESC : OrderMode.ASC);
        search(null);
        priceIcon.setGlyphName(currentOrdering.getMode() == OrderMode.ASC ? "SORT_ASC" : "SORT_DESC");
    }

    public void addBook(MouseEvent mouseEvent) {
        dialogControl.show(Main.getRoot());
    }
}
