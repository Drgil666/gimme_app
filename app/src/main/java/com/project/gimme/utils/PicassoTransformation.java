package com.project.gimme.utils;

import android.graphics.Bitmap;

import com.project.gimme.GimmeApplication;
import com.squareup.picasso.Transformation;

public class PicassoTransformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth = GimmeApplication.getWeight();
        int targetHeight = GimmeApplication.getHeight();
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        if (sourceWidth > sourceHeight) {
            double siz = targetWidth * 1.0 / sourceWidth;
            sourceWidth = targetWidth;
            sourceHeight = (int) (sourceHeight * siz);
        } else {
            double siz = targetHeight * 1.0 / sourceHeight;
            sourceHeight = targetHeight;
            sourceWidth = (int) (sourceWidth * siz);
        }
        Bitmap result = Bitmap.createScaledBitmap(source, sourceWidth, sourceHeight, false);
        if (result != source) {
            // Same bitmap is returned if sizes are the same
            source.recycle();
        }
        return result;
    }

    @Override
    public String key() {
        return "desiredWidth" + " desiredHeight";
    }
}
