package com.aliceapps.espressoutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class TestUtil {
    @Nullable
    public static Bitmap getBitmapFromVectorID(Context context, int drawableId, View view) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable != null) {
            if (drawable.getIntrinsicWidth() > 0 && drawable.getIntrinsicHeight() > 0)
                return getBitmapFromVectorDrawable(drawable, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
            else
                return getBitmapFromVectorDrawable(drawable, view.getWidth(),
                        view.getHeight());
        } else
            return null;
    }

    public static Bitmap getBitmapFromVectorDrawable(@NonNull Drawable drawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
