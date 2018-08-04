/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.baidu.tripsight.R;

import static android.graphics.Paint.Style.FILL;

/**
 * place holder
 *
 * @author zhangtianjun
 */
public class PlaceholderDrawable extends Drawable {
    private final Paint backgroundPaint = new Paint();
    private final Bitmap indicatorBitmap;
    private final RectF bounds = new RectF();
    private final RectF imageBounds = new RectF();

    public static Drawable create(Context context) {
        return new PlaceholderDrawable(context);
    }

    private PlaceholderDrawable(Context context) {
        Resources resources = context.getResources();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(FILL);
        backgroundPaint.setColor(resources.getColor(R.color.image_placeholder));
        indicatorBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_placeholder);
    }

    @Override
    public void draw(Canvas canvas) {
        drawBackground(canvas);
        drawIndicator(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(bounds, backgroundPaint);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.bounds.setEmpty();
        this.bounds.left = bounds.left;
        this.bounds.right = bounds.right;
        this.bounds.top = bounds.top;
        this.bounds.bottom = bounds.bottom;

        this.imageBounds.setEmpty();

        float imageWidth;
        if (bounds.width() <= 200) {
            imageWidth = 0;
        } else if (bounds.width() <= 400) {
            imageWidth = bounds.width() / 4;
        } else {
            imageWidth = Math.min(bounds.width() / 6f, indicatorBitmap.getWidth());
        }
        float imageHeight = indicatorBitmap.getHeight() * imageWidth / indicatorBitmap.getWidth();
        this.imageBounds.left = bounds.left + (bounds.width() - imageWidth) / 2f;
        this.imageBounds.right = imageBounds.left + imageWidth;
        this.imageBounds.top = bounds.top + (bounds.height() - imageHeight) / 2f;
        this.imageBounds.bottom = imageBounds.top + imageHeight;
    }

    private void drawIndicator(Canvas canvas) {
        canvas.drawBitmap(indicatorBitmap, null, imageBounds, null);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
