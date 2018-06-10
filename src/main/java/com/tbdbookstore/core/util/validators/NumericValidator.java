package com.tbdbookstore.core.util.validators;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.DefaultProperty;
import javafx.scene.control.TextInputControl;

@DefaultProperty(value = "icon")
public class NumericValidator extends ValidatorBase {
    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl)
            evalTextInputField();
    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        String text = textField.getText();
        hasErrors.set(false);
        if (!text.isEmpty()) {
            for (char c : text.toCharArray())
                if (!Character.isDigit(c)) {
                    hasErrors.set(true);
                    break;
                }
        }
    }
}
