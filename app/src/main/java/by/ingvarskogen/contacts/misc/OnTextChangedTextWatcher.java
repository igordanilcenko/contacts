package by.ingvarskogen.contacts.misc;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Simple text watcher handling only on text changed
 */
public abstract class OnTextChangedTextWatcher implements TextWatcher {
    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChanged(s.toString());
    }

    @Override public void afterTextChanged(Editable s) {
    }

    protected abstract void onTextChanged(String s);
}
