package com.aliceapps.espressoutils;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

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

    public static class ReplaceAutocompleteTextAction implements ViewAction {
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

    //Click on view - same as click(), but doesn't check constraints
    @NonNull
    public static ViewAction clickOnView() {
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isEnabled(); // no constraints, they are checked above
            }

            @Override
            public String getDescription() {
                return "Click on view";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performClick();
            }
        };
    }
}
