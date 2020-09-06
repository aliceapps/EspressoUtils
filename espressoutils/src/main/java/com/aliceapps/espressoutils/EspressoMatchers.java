package com.aliceapps.espressoutils;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.test.espresso.matcher.BoundedMatcher;

import com.google.android.material.textfield.TextInputLayout;
import org.hamcrest.Matcher;
import org.hamcrest.Description;

import java.util.ArrayList;
import java.util.List;

public class EspressoMatchers {
    @NonNull
    public static Matcher<View> hasErrorText(final String expectedErrorText) {
        return new BoundedMatcher<View, TextInputLayout>(TextInputLayout.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Checking received view has error message: " + expectedErrorText);
            }

            @Override
            protected boolean matchesSafely(TextInputLayout view) {
                CharSequence error = view.getError();

                if (error == null) {
                    return false;
                }
                String hint = error.toString();
                return expectedErrorText.equals(hint);
            }
        };
    }

    @NonNull
    public static Matcher<View> noErrorText() {
        return new BoundedMatcher<View, TextInputLayout>(TextInputLayout.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Checking received view doesn't have error message");
            }

            @Override
            protected boolean matchesSafely(TextInputLayout item) {
                CharSequence error = item.getError();
                return error == null || error == "";
            }
        };
    }

    @SuppressWarnings("rawtypes")
    @NonNull
    public static Matcher<View> autocompleteContainsValues(final String expectedText) {
        return new BoundedMatcher<View, AutoCompleteTextView>(AutoCompleteTextView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking autocomplete array contains correct values");
            }

            @Override
            protected boolean matchesSafely(AutoCompleteTextView item) {
                ArrayAdapter adapter = (ArrayAdapter) item.getAdapter();
                List<CharSequence> options = new ArrayList<>();

                for(int i=0 ; i<adapter.getCount() ; i++) {
                    options.add((CharSequence) adapter.getItem(i));
                }

                if (options.size() == 0)
                    return false;

                return options.contains(expectedText);
            }
        };
    }
}
