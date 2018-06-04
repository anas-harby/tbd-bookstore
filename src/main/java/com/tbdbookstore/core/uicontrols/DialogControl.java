package com.tbdbookstore.core.uicontrols;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;
import java.io.IOException;

@ViewController(value = "/com/tbdbookstore/view/fxml/AddBookDialog.fxml")
public class DialogControl extends JFXDialog {

    public static final String CONTENT_PANE = "ContentPane";
    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private JFXDialog dialog;
    @FXML
    private StackPane root;

    public DialogControl() {

    }

    public void show(Node node) {
    }


}
