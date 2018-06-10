package com.tbdbookstore.core.util.validators;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.DefaultProperty;
import javafx.scene.control.TextInputControl;

@DefaultProperty(value = "icon")
public class CreditCardValidator extends ValidatorBase {
    private static final String REGEX = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]" +
            "{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])" +
            "[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl)
            evalTextInputField();
    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        String text = textField.getText();
        hasErrors.set(true);
        if (!text.isEmpty())
            hasErrors.set(!text.matches(REGEX));
    }
}
