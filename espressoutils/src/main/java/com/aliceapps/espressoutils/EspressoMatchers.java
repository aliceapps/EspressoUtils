package com.aliceapps.espressoutils;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.test.espresso.matcher.BoundedMatcher;

import com.google.android.material.textfield.TextInputLayout;
import org.hamcrest.Matcher;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;

public class EspressoMatchers {
    @NonNull
    public static Matcher<View> hasErrorText(final int expectedErrorId) {
        return new BoundedMatcher<View, TextInputLayout>(TextInputLayout.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Checking received view has error message: " + expectedErrorId);
            }

            @Override
            protected boolean matchesSafely(TextInputLayout view) {
                CharSequence error = view.getError();

                if (error == null) {
                    return false;
                }
                String hint = error.toString();
                String expectedError = view.getContext().getResources().getString(expectedErrorId);
                return expectedError.equals(hint);
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

    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
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

    @NonNull
    public static Matcher<? super View> hasBackgroundImage(final int imageID) {
        return new BoundedMatcher<View, View>(View.class) {

            /**
             * Generates a description of the object.  The description may be part of a
             * a description of a larger object of which this is just a component, so it
             * should be worded appropriately.
             *
             * @param description The description to be built or appended to.
             */
            @Override
            public void describeTo(Description description) {
                description.appendText("Checking layout has background " + imageID);
            }

            @Override
            protected boolean matchesSafely(View item) {
                Bitmap expected = TestUtil.getBitmapFromVectorID(item.getContext(), imageID);
                Bitmap actual = TestUtil.getBitmapFromVectorDrawable(item.getBackground());
                return expected != null && actual != null && expected.sameAs(actual);
            }
        };
    }

    @NonNull
    public static Matcher<? super View> hasTextColor(final int color) {
        return new BoundedMatcher<View, TextView>(TextView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking view is has correct text color.");
                description.appendText("Color id: " + color);
            }

            @Override
            protected boolean matchesSafely(TextView imageView) {
                int currentColor = imageView.getCurrentTextColor();
                return  currentColor == ContextCompat.getColor(imageView.getContext(),color);
            }
        };
    }
}
