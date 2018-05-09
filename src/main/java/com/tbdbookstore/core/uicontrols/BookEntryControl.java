package com.tbdbookstore.core.uicontrols;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookEntryControl extends RecursiveTreeObject<BookEntryControl> {
    private final StringProperty isbn;
    private final StringProperty title;
    private final StringProperty author;
    private final IntegerProperty year;
    private final StringProperty genre;
    private final StringProperty publisher;
    private final IntegerProperty quantity;

    public BookEntryControl(String isbn, String title, String author,
                            int year, String genre, String publisher, int quantity) {
        this.isbn = new SimpleStringProperty(isbn);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.year = new SimpleIntegerProperty(year);
        this.genre = new SimpleStringProperty(genre);
        this.publisher = new SimpleStringProperty(publisher);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public BookEntryControl() {
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

    public StringProperty isbnProperty() {
        return isbn;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public int getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public String getPublisher() {
        return publisher.get();
    }

    public StringProperty publisherProperty() {
        return publisher;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
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
