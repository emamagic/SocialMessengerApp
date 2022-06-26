package com.emamagic.base.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import com.emamagic.base.onTextChange
import com.emamagic.base.R

class LimooValidatorInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    init { init(attrs) }

    private var titleTextView: TextView? = null
    private var validatorTextView: TextView? = null
    private var inputEditText: EditText? = null

    @IdRes
    private var titleTextId: Int? = null
    @IdRes
    private var validatorTextId: Int? = null
    @IdRes
    private var inputEditTextId: Int? = null

    private var bgDefaultState: Drawable? = null

    private var bgFocusedState: Drawable? = null

    private var bgFailedState: Drawable? = null

    private fun init(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LimooValidatorInput)
        bgDefaultState = typedArray.getDrawable(R.styleable.LimooValidatorInput_state_default)
        bgFocusedState = typedArray.getDrawable(R.styleable.LimooValidatorInput_state_focused)
        bgFailedState = typedArray.getDrawable(R.styleable.LimooValidatorInput_state_fail)
        typedArray.recycle()
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
        if (titleTextId == null) {
            titleTextId = child?.id
            return
        }
        if (inputEditTextId == null) {
            inputEditTextId = child?.id
            return
        }
        if (validatorTextId == null) {
            validatorTextId = child?.id
            return
        }
    }

    private fun findViews() {
        titleTextView = findViewById(titleTextId!!)
        validatorTextView = findViewById(validatorTextId!!)
        inputEditText = findViewById(inputEditTextId!!)
        inputEditText?.background = bgDefaultState
        onTextChange()
        onFocusChange()
    }

    override fun onFinishInflate() {
        findViews()
        super.onFinishInflate()
    }

    private fun onTextChange() {
        inputEditText?.onTextChange {
            titleTextView?.setTextColor(ContextCompat.getColor(context, R.color.limoo_secondary_dark))
            if (it.isNotEmpty()) {
                inputEditText?.background = bgFocusedState
                validatorTextView?.visibility = View.INVISIBLE
            } else {
                inputEditText?.background = bgDefaultState
                validatorTextView?.visibility = View.INVISIBLE

            }
        }
    }

    private fun onFocusChange() {
        inputEditText?.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputEditText?.background = bgFocusedState
            } else {
                inputEditText?.background = bgDefaultState
            }
        }
    }

    fun invalidateInput(message: String? = null) {
        inputEditText?.background = bgFailedState
        message?.let { validatorTextView?.text = it }
        titleTextView?.setTextColor(ContextCompat.getColor(context, R.color.limoo_error_state))
        validatorTextView?.setTextColor(ContextCompat.getColor(context, R.color.limoo_error_state))
        validatorTextView?.visibility = View.VISIBLE
    }

    fun reset() {
        inputEditText?.background = bgFocusedState
        inputEditText?.setText("")
        validatorTextView?.visibility = View.INVISIBLE

    }

}