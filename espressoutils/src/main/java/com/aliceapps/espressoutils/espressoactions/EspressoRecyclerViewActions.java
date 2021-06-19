package com.aliceapps.espressoutils.espressoactions;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static org.hamcrest.Matchers.allOf;

public class EspressoRecyclerViewActions {

    @NonNull
    public static ViewAction clickOnChildViewWithId(final int id) {
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isEnabled();
            }

            @Override
            public String getDescription() {
                return "Click on button inside recycler view item";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    @NonNull
    public static ViewAction replaceTextOnChildView(final int id, final String s) {
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public String getDescription() {
                return "Replace test inside view item";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView v = view.findViewById(id);
                v.setText(s);
            }
        };
    }

    @NonNull
    public static ViewAction waitRecyclerView(final int itemCount, final long millis) {
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return isEnabled();
            }

            @Override
            public String getDescription() {
                return "wait for a recycler view to load items <" + itemCount + "> during " + millis + " millis.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;

                RecyclerView recyclerView = (RecyclerView) view;

                do {
                    if (recyclerView.getAdapter() != null && recyclerView.getAdapter().getItemCount() == itemCount)
                        return;

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);
            }
        };
    }
}
