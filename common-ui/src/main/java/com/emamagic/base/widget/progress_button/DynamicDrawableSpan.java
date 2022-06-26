package com.emamagic.base.widget.progress_button;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DynamicDrawableSpan extends android.text.style.DynamicDrawableSpan {
    private Drawable drawable;

    public DynamicDrawableSpan(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        Rect rect = getDrawable().getBounds();
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        if (fm != null) {

            // get a font height
            int lineHeight = fontMetricsInt.bottom - fontMetricsInt.top;

            //make sure our drawable has height >= font height
            int drHeight = Math.max(lineHeight, rect.bottom - rect.top);

            int centerY = fontMetricsInt.top + lineHeight / 2;

            //adjust font metrics to fit our drawable size
            fm.ascent = centerY - drHeight / 2;
            fm.descent = centerY + drHeight / 2;
            fm.top = fm.ascent;
            fm.bottom = fm.descent;
        }
        return rect.width();
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        canvas.save();
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // get font height. in our case now it's drawable height
        int lineHeight = fontMetrics.bottom - fontMetrics.top;

        // adjust canvas vertically to draw drawable on text vertical center
        int centerY = y + fontMetrics.bottom - lineHeight / 2;
        int transY = centerY - getDrawable().getBounds().height() / 2;

        // adjust canvas horizontally to draw drawable with defined margin from text
        canvas.translate(x, transY);

        getDrawable().draw(canvas);

        canvas.restore();
    }

    @Override
    public Drawable getDrawable() {
        return drawable;
    }
}
