package com.emamagic.base.helpers

import android.app.Application
import android.graphics.Typeface
import android.text.TextPaint
import android.widget.TextView

object TypeFaceHelper {

    const val IRAN_YEKAN_REG = "fonts/IRANYekanMobileRegular.ttf"
    const val IRAN_YEKAN_BOLD = "fonts/IRANYekanMobileBold.ttf"
    private var mTypeFaceNormal: Typeface? = null
    private var mTypeFaceBold: Typeface? = null

    fun init(application: Application) {
        mTypeFaceNormal = Typeface.createFromAsset(
            application.assets,
//            if (PERSIAN.equals(Prefs.getAppLanguage())) "fonts/IRANYekanMobileRegular.ttf" else "fonts/rmono.ttf"
             IRAN_YEKAN_REG
        )
        mTypeFaceBold = Typeface.createFromAsset(
            application.assets,
             IRAN_YEKAN_BOLD
        )
    }

    @JvmStatic
    fun applyTypeface(textView: TextView) {
        textView.typeface = if (textView.typeface == null
            || textView.typeface.style == Typeface.NORMAL
        ) mTypeFaceNormal else mTypeFaceBold
    }

    fun applyTypeface(textPaint: TextPaint) {
        textPaint.isFakeBoldText = true
        textPaint.typeface = mTypeFaceBold
    }

    fun getTypeface(): Typeface? {
        return mTypeFaceNormal
    }

    fun applyNormalTypeface(textView: TextView) {
        textView.typeface = mTypeFaceNormal
    }

    fun applyBoldTypeface(textView: TextView) {
        textView.typeface = mTypeFaceBold
    }

}