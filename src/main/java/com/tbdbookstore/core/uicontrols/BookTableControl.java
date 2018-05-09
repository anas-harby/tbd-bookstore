package com.tbdbookstore.core.uicontrols;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeTableColumn;

import java.io.IOException;
import java.util.function.Function;

public class BookTableControl extends JFXTreeTableView {
    @FXML private JFXTreeTableView<BookEntry> root;
    @FXML private JFXTreeTableColumn<BookEntry, String> isbnCol;
    @FXML private JFXTreeTableColumn<BookEntry, String> titleCol;
    @FXML private JFXTreeTableColumn<BookEntry, String> authorCol ;
    @FXML private JFXTreeTableColumn<BookEntry, Integer> yearCol;
    @FXML private JFXTreeTableColumn<BookEntry, String> genreCol;
    @FXML private JFXTreeTableColumn<BookEntry, String> publisherCol;
    @FXML private JFXTreeTableColumn<BookEntry, Integer> quantityCol;

    private ObservableList<BookEntry> data;

    public BookTableControl() {
        loadFxml();

        setupCellValueFactory(isbnCol, BookEntry::isbnProperty);
        setupCellValueFactory(titleCol, BookEntry::titleProperty);
        setupCellValueFactory(authorCol, BookEntry::authorProperty);
        setupCellValueFactory(yearCol, b -> b.yearProperty().asObject());
        setupCellValueFactory(genreCol, BookEntry::genreProperty);
        setupCellValueFactory(publisherCol, BookEntry::publisherProperty);
        setupCellValueFactory(quantityCol, b -> b.quantityProperty().asObject());

        data = FXCollections.observableArrayList();
        root.setRoot(new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren));
        root.setShowRoot(false);
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/com/tbdbookstore/view/fxml/BookTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<BookEntry, T> col,
                                           Function<BookEntry, ObservableValue<T>> mapper) {
        col.setCellValueFactory((TreeTableColumn.CellDataFeatures<BookEntry, T> param) -> {
            if (col.validateValue(param))
                return mapper.apply(param.getValue().getValue());
            else
                return col.getComputedValue(param);
        });
    }

    public void generate(int n) {
        for (int i = 0; i < n; i++) {
            data.add(new BookEntry());
            final IntegerProperty currCountProp = root.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() + 1);
        }
    }

    class BookEntry extends RecursiveTreeObject<BookEntry> {
        private final StringProperty isbn;
        private final StringProperty title;
        private final StringProperty author;
        private final IntegerProperty year;
        private final StringProperty genre;
        private final StringProperty publisher;
        private final IntegerProperty quantity;

        BookEntry(String isbn, String title, String author,
                        int year, String genre, String publisher, int quantity) {
            this.isbn = new SimpleStringProperty(isbn);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.year = new SimpleIntegerProperty(year);
            this.genre = new SimpleStringProperty(genre);
            this.publisher = new SimpleStringProperty(publisher);
            this.quantity = new SimpleIntegerProperty(quantity);
        }

        BookEntry() {
            this.isbn = new SimpleStringProperty("1234");
            this.title = new SimpleStringProperty("title");
            this.author = new SimpleStringProperty("author");
            this.year = new SimpleIntegerProperty(1999);
            this.genre = new SimpleStringProperty("genre");
            this.publisher = new SimpleStringProperty("publisher");
            this.quantity = new SimpleIntegerProperty(5);
        }

        public String getIsbn() {
            return isbn.get();
        }

        StringProperty isbnProperty() {
            return isbn;
        }

        public String getTitle() {
            return title.get();
        }

        StringProperty titleProperty() {
            return title;
        }

        public String getAuthor() {
            return author.get();
        }

        StringProperty authorProperty() {
            return author;
        }

        public int getYear() {
            return year.get();
        }

        IntegerProperty yearProperty() {
            return year;
        }

        public String getGenre() {
            return genre.get();
        }

        StringProperty genreProperty() {
            return genre;
        }

        public String getPublisher() {
            return publisher.get();
        }

        StringProperty publisherProperty() {
            return publisher;
        }

        public int getQuantity() {
            return quantity.get();
        }

        IntegerProperty quantityProperty() {
            return quantity;
        }

        public void setIsbn(String isbn) {
            this.isbn.set(isbn);
        }

        public void setTitle(String title) {
            this.title.set(title);
        }

        public void setAuthor(String author) {
            this.author.set(author);
        }

        public void setYear(int year) {
            this.year.set(year);
        }

        public void setGenre(String genre) {
            this.genre.set(genre);
        }

        public void setPublisher(String publisher) {
            this.publisher.set(publisher);
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }
    }
}
