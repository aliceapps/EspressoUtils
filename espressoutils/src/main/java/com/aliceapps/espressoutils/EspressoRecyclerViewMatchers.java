package com.aliceapps.espressoutils;

import android.graphics.Bitmap;
import android.graphics.drawable.VectorDrawable;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Objects;

public class EspressoRecyclerViewMatchers {

    @NonNull
    public static Matcher<View> recycleViewHasItems(final int size) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking recycler view has items: " + size);
            }

            @Override
            protected boolean matchesSafely(@NonNull RecyclerView item) {
                if (item.getAdapter() == null)
                    return false;
                else {
                    int count = item.getAdapter().getItemCount();
                    return count == size;
                }
            }
        };
    }

    @NonNull
    public static Matcher<? super View> itemAtPositionChecked(final int position, final int viewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking recycler view item is checked. Item position: " + position);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                CheckedTextView check = (CheckedTextView) getItemViewElement(viewId, position, item);
                if (check == null)
                    return false;
                else
                    return check.isChecked();
            }
        };
    }

    @Nullable
    private static View getItemViewElement(int id, int position, @NonNull RecyclerView item) {
        if (item.findViewHolderForAdapterPosition(position) == null)
            return null;
        else {
            View childView = Objects.requireNonNull(item.findViewHolderForAdapterPosition(position)).itemView;
            return childView.findViewById(id);
        }
    }

    @NonNull
    public static Matcher<? super View> itemAtPositionFilled(final String checkText, final int position, final int viewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking recycler view item is filled. Item position: " + position);
                description.appendText("Item name: " + checkText);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                TextView check = (TextView) getItemViewElement(viewId, position, item);
                if (check == null)
                    return false;
                else {
                    String found = check.getText().toString();
                    return found.equals(checkText);
                }
            }
        };
    }

    @NonNull
    public static Matcher<? super View> itemAtPositionImage(final int imageId, final int position, final int viewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking recycler view item is has correct image. Item position: " + position);
                description.appendText("Item image id: " + imageId);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                View check = getItemViewElement(viewId, position, item);
                if (check == null)
                    return false;
                else {
                    Bitmap image = TestUtil.getBitmapDrawableFromID(imageId).getBitmap();
                    Bitmap finalImage = TestUtil.getBitmapDrawableFromVector((VectorDrawable) check.getBackground());
                    return image.sameAs(finalImage);
                }
            }
        };
    }

    @NonNull
    public static Matcher<? super View> itemAtPositionHidden(final int position) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking recycler view item is hidden. Item position: " + position);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                if (item.findViewHolderForAdapterPosition(position) == null)
                    return true;
                else {
                    View check = Objects.requireNonNull(item.findViewHolderForAdapterPosition(position)).itemView;
                    return check.getVisibility() == View.GONE;
                }
            }
        };
    }

}
