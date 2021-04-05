package com.aliceapps.espressoutils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import com.google.android.material.chip.Chip;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Objects;

public class EspressoRecyclerViewMatchers {

    @NonNull
    public static Matcher<View> recycleViewSize(final int size) {
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
    public static Matcher<? super View> checkedTextViewChecked(final int position, final int viewId) {
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
    public static Matcher<? super View> textViewHasData(final String checkText, final int position, final int viewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking recycler view item is filled. Item position: " + position);
                description.appendText("Item name: " + checkText);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                TextView check = (TextView) getItemViewElement(viewId, position, item);
                if (check == null) {
                    return false;
                } else {
                    String found = check.getText().toString();
                    return found.equals(checkText);
                }
            }
        };
    }

    @NonNull
    public static Matcher<? super View> viewHasTag(final String tag, final int position, final int viewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking recycler view item is has correct image. Item position: " + position);
                description.appendText("Item image tag: " + tag);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                View imageView = getItemViewElement(viewId, position, item);
                if (imageView == null || imageView.getTag() == null)
                    return false;
                else {
                    return imageView.getTag().equals(tag);
                }
            }
        };
    }

    @NonNull
    public static Matcher<? super View> chipVisible(final int position, final int viewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking recycler view item is has correct image. Item position: " + position);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                View imageView = getItemViewElement(viewId, position, item);
                if (!(imageView instanceof Chip)) {
                    return false;
                } else {
                    Chip chip = (Chip) imageView;
                    return chip.getVisibility() == View.VISIBLE;
                }
            }
        };
    }

    @NonNull
    public static Matcher<? super View> imageViewHasBackground(final int expectedImage, final int position, final int viewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking recycler view item is has correct image. Item position: " + position);
                description.appendText("Item image id: " + expectedImage);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                View imageView = getItemViewElement(viewId, position, item);
                if (! (imageView instanceof ImageView)) {
                    return false;
                } else {
                    return compareDrawableToResource(imageView.getBackground(), expectedImage, imageView);
                }
            }
        };
    }

    @NonNull
    public static Matcher<? super View> textViewHasCompoundDrawable(final int expectedImage, final int position, final int viewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking recycler view item is has correct image. Item position: " + position);
                description.appendText("Item image id: " + expectedImage);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                View imageView = getItemViewElement(viewId, position, item);
                if (!(imageView instanceof TextView)) {
                    return false;
                } else {
                    boolean found = false;
                        TextView textView = (TextView) imageView;
                        Drawable[] drawables = textView.getCompoundDrawables();

                        for (Drawable drawable : drawables) {
                            if (!found && drawable != null)
                                found = compareDrawableToResource(drawable, expectedImage, imageView);
                        }
                    return found;
                }
            }
        };
    }

    private static boolean compareDrawableToResource(Drawable drawable, int expectedId, @NonNull View view) {
        if (drawable == null || expectedId == 0)
            return false;
        Bitmap expectedImage = TestUtil.getBitmapFromVectorID(view.getContext(), expectedId, view);
        Bitmap actualImage = TestUtil.getBitmapFromVectorDrawable(drawable, view.getWidth(), view.getHeight());
        return expectedImage != null && actualImage.sameAs(expectedImage);
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

    @NonNull
    public static Matcher<? super View> childItemAtPositionVisible(final int position, final int viewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Checking recycler view item is has correct image. Item position: " + position);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                View imageView = getItemViewElement(viewId, position, item);
                if (imageView == null) {
                    return false;
                } else {
                    return imageView.getVisibility() == View.VISIBLE;
                }
            }
        };
    }

}
