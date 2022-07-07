package com.emamagic.common_ui.helpers

import android.R
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import java.util.*

object ViewHelper {

    @JvmStatic
    fun dpToPx(context: Context, dp: Float): Int {
        return (dp * context.resources.displayMetrics.density + 0.5f).toInt()
    }

    @JvmStatic
    fun tintDrawable(drawable: Drawable, @ColorInt color: Int) {
        drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    @JvmStatic
    fun isRTL(): Boolean {
        val defLocale = Locale.getDefault()
        val directionality = Character.getDirectionality(defLocale.displayName[0]).toInt()
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
    }

    @JvmStatic
    @ColorInt
    fun getTertiaryTextColor(context: Context): Int {
        return getColorAttr(context, R.attr.textColorTertiary)
    }

    @ColorInt
    private fun getColorAttr(context: Context, attr: Int): Int {
        val theme = context.theme
        val typedArray = theme.obtainStyledAttributes(intArrayOf(attr))
        val color = typedArray.getColor(0, Color.LTGRAY)
        typedArray.recycle()
        return color
    }

}