package com.tbdbookstore.core.util.validators;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.DefaultProperty;
import javafx.scene.control.TextInputControl;

@DefaultProperty(value = "icon")
public class ExpiryDateValidator extends ValidatorBase {
    private static final String REGEX = "^(0[1-9]|1[0-2])/?([0-9]{4}|[0-9]{2})$";

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl)
            evalTextInputField();
    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        String text = textField.getText().trim();
        hasErrors.set(true);
        if (!text.isEmpty())
            hasErrors.set(!text.matches(REGEX));
    }
}
