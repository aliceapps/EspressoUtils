package com.aliceapps.espressoutils.espressoactions;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.Matchers.allOf;

@SuppressWarnings("rawtypes")
public class EspressoActions {
    public static ViewAction replaceAutocomplete(@NonNull String stringToBeSet) {
        return actionWithAssertions(new ReplaceAutocompleteTextAction(stringToBeSet));
    }

    private static class ReplaceAutocompleteTextAction implements ViewAction {
        private final String stringToBeSet;

        ReplaceAutocompleteTextAction(String value) {
            checkNotNull(value);
            this.stringToBeSet = value;
        }

        @Override
        public Matcher<View> getConstraints() {
            return allOf(isDisplayed(), isAssignableFrom(AutoCompleteTextView.class));
        }

        @Override
        public void perform(UiController uiController, View view) {
            AutoCompleteTextView autocomplete = (AutoCompleteTextView) view;

            ArrayAdapter adapter = (ArrayAdapter) autocomplete.getAdapter();
            autocomplete.setAdapter(null);

            ((EditText) view).setText(stringToBeSet);

            autocomplete.setAdapter(adapter);
        }

        @Override
        public String getDescription() {
            return "replace text without showing autocomplete suggestions";
        }
    }

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
            }
            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }

}
