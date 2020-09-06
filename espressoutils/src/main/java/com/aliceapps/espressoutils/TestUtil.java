package com.aliceapps.espressoutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.test.platform.app.InstrumentationRegistry;

import org.jetbrains.annotations.Nullable;

public class TestUtil {
    @NonNull
    public static BitmapDrawable getBitmapDrawableFromID(int id) {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getContext();
        final Bitmap bitmap = getBitmapFromVectorID(targetContext, id);
        return new BitmapDrawable(targetContext.getResources(), bitmap);
    }

    @NonNull
    public static Bitmap getBitmapDrawableFromVector(VectorDrawable drawable) {
        return getBitmapFromVectorDrawable(drawable);
    }

    @Nullable
    private static Bitmap getBitmapFromVectorID(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable != null) {
            return getBitmapFromVectorDrawable(drawable);
        } else
            return null;
    }

    private static Bitmap getBitmapFromVectorDrawable(@NonNull Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
