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
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

public class ViewController implements Initializable {
    @FXML private JFXTreeTableView<BookEntryControl> table;
    @FXML private JFXTreeTableColumn<BookEntryControl, String> isbnCol;
    @FXML private JFXTreeTableColumn<BookEntryControl, String> titleCol;
    @FXML private JFXTreeTableColumn<BookEntryControl, String> authorCol ;
    @FXML private JFXTreeTableColumn<BookEntryControl, Integer> yearCol;
    @FXML private JFXTreeTableColumn<BookEntryControl, String> genreCol;
    @FXML private JFXTreeTableColumn<BookEntryControl, String> publisherCol;
    @FXML private JFXTreeTableColumn<BookEntryControl, Integer> quantityCol;

    private ObservableList<BookEntryControl> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCellValueFactory(isbnCol, BookEntryControl::isbnProperty);
        setupCellValueFactory(titleCol, BookEntryControl::titleProperty);
        setupCellValueFactory(authorCol, BookEntryControl::authorProperty);
        setupCellValueFactory(yearCol, b -> b.yearProperty().asObject());
        setupCellValueFactory(genreCol, BookEntryControl::genreProperty);
        setupCellValueFactory(publisherCol, BookEntryControl::publisherProperty);
        setupCellValueFactory(quantityCol, b -> b.quantityProperty().asObject());

        data = FXCollections.observableArrayList();

        table.setRoot(new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren));
        table.setShowRoot(false);
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
    @FXML
    public void click(MouseEvent event) {
        System.out.println("CLICK");

        for (int i = 0; i < 10; i++) {
            data.add(new BookEntryControl());
            final IntegerProperty currCountProp = table.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() + 1);
        }
    }
}
