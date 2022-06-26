package com.emamagic.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;
import com.emamagic.base.helpers.TypeFaceFactory;

public class FontButton extends AppCompatButton {

    public FontButton(Context context) {
        super(context);
        init();
    }

    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }
        TypeFaceFactory.applyTypeface(this);
    }
}
