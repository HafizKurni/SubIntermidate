package com.dicoding.subintermidate.feature

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.subintermidate.R

class CustomEditText : AppCompatEditText {

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (inputType == 129) {
                    if (p0.toString().isNotEmpty() && p0.toString().length < 8) showError(
                        context.getString(
                            R.string.password_strict
                        )) else hideError()
                } else if (inputType == 33) {
                    if (p0.toString().isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(p0).matches()) showError(
                        context.getString(
                            R.string.incorrect_format
                        )) else hideError()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun showError(message: String) {
        error = message
    }

    private fun hideError() {
        error = null
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


}