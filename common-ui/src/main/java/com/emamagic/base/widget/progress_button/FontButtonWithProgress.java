package com.emamagic.base.widget.progress_button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import com.emamagic.base.helpers.ViewHelper;
import com.emamagic.base.R;
import com.emamagic.base.widget.FontButton;

public class FontButtonWithProgress extends FontButton {
    DynamicDrawableSpan progressDrawableSpan;
    DynamicDrawableSpan doneDrawableSpan;
    private CharSequence textBeforeLoading;
    private boolean isShowingProgress = false;

    public FontButtonWithProgress(Context context) {
        this(context, null);
    }

    public FontButtonWithProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontButtonWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray tp = context.obtainStyledAttributes(attrs, R.styleable.FontButtonWithProgress);
            boolean isEnable = tp.getBoolean(R.styleable.FontButtonWithProgress_is_enable, false);
            if (isEnable) enable();
            else disable();
            tp.recycle();
        }
        int progressDrawableSize = ViewHelper.dpToPx(context, 15);
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(ViewHelper.dpToPx(context,3));
        progressDrawable.setCenterRadius(ViewHelper.dpToPx(context,10));
        progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
        progressDrawable.start();
        progressDrawable.setBounds(0, 0, progressDrawableSize, progressDrawableSize);
        progressDrawable.setCallback(new Drawable.Callback() {
            @Override
            public void invalidateDrawable(@NonNull Drawable who) {
                FontButtonWithProgress.this.invalidate();
            }

            @Override
            public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {

            }

            @Override
            public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {

            }
        });

        this.progressDrawableSpan = new DynamicDrawableSpan(progressDrawable);

        Drawable doneDrawable = ContextCompat.getDrawable(context, R.drawable.ic_tick);
        int doneDrawableSize = ViewHelper.dpToPx(context, 25);
        if (doneDrawable != null) {
            doneDrawable.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
            doneDrawable.setBounds(0, 0, doneDrawableSize, doneDrawableSize);
        }
        this.doneDrawableSpan = new DynamicDrawableSpan(doneDrawable);
    }

    public void showProgress() {
        showProgress("");
    }

    public void showProgress(String loadingText) {
        if (isShowingProgress) return;
        isShowingProgress = true;
        setEnabled(false);
        textBeforeLoading = getText();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(loadingText);
        spannableStringBuilder.append("   ");
        spannableStringBuilder.setSpan(progressDrawableSpan, spannableStringBuilder.length() - 1,
                spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannableStringBuilder);
    }

    public void showDone() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(" ");
        spannableStringBuilder.setSpan(doneDrawableSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannableStringBuilder);
    }

    public void hideProgress() {
        if (!isShowingProgress) return;
        setEnabled(true);
        setText(textBeforeLoading);
        isShowingProgress = false;
    }

    public void enable() {
        setEnabled(true);
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_limoo_enable_submit));
    }

    public void disable() {
        setEnabled(false);
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_limoo_disable_submit));
    }


}
