package com.tbdbookstore.core.control;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.tbdbookstore.core.uicontrols.BookEntryControl;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeTableColumn;

import java.io.IOException;
import java.util.function.Function;

public class BookTableController extends JFXTreeTableView {
    @FXML private JFXTreeTableView<BookEntryControl> root;
    @FXML private JFXTreeTableColumn<BookEntryControl, String> isbnCol;
    @FXML private JFXTreeTableColumn<BookEntryControl, String> titleCol;
    @FXML private JFXTreeTableColumn<BookEntryControl, String> authorCol ;
    @FXML private JFXTreeTableColumn<BookEntryControl, Integer> yearCol;
    @FXML private JFXTreeTableColumn<BookEntryControl, String> genreCol;
    @FXML private JFXTreeTableColumn<BookEntryControl, String> publisherCol;
    @FXML private JFXTreeTableColumn<BookEntryControl, Integer> quantityCol;

    private ObservableList<BookEntryControl> data;


    public BookTableController() {
        loadFxml();

        setupCellValueFactory(isbnCol, BookEntryControl::isbnProperty);
        setupCellValueFactory(titleCol, BookEntryControl::titleProperty);
        setupCellValueFactory(authorCol, BookEntryControl::authorProperty);
        setupCellValueFactory(yearCol, b -> b.yearProperty().asObject());
        setupCellValueFactory(genreCol, BookEntryControl::genreProperty);
        setupCellValueFactory(publisherCol, BookEntryControl::publisherProperty);
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

    private <T> void setupCellValueFactory(JFXTreeTableColumn<BookEntryControl, T> col,
                                           Function<BookEntryControl, ObservableValue<T>> mapper) {
        col.setCellValueFactory((TreeTableColumn.CellDataFeatures<BookEntryControl, T> param) -> {
            if (col.validateValue(param))
                return mapper.apply(param.getValue().getValue());
            else
                return col.getComputedValue(param);
        });
    }

    public void generate(int n) {
        for (int i = 0; i < n; i++) {
            data.add(new BookEntryControl());
            final IntegerProperty currCountProp = root.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() + 1);
        }
    }
}
