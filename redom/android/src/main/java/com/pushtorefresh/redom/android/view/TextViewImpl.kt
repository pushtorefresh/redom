package com.pushtorefresh.redom.android.view

import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.TextView
import java.util.*

open class TextViewImpl : TextView, ViewImpl() {
    override var gravity = EnumSet.of(TextView.Gravity.Top, TextView.Gravity.Left)
    override var text: CharSequence = ""
    override var onTextChange: ((CharSequence) -> Unit)? = null

    override fun build(): BaseComponent<*, *> {
        return Component(
            binder = ::bindTextView,
            dslView = this,
            clazz = TextView::class.java
        )
    }

}

fun bindTextView(dslText: TextView, textView: android.widget.TextView): Binding {
    val viewBinding = bindView(dslText, textView)

    if (textView.text.toString() != dslText.text) {
        // TODO do it with Domic-like diffing
        textView.text = dslText.text
    }
    val gravity = dslText.gravity.map {
        @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
        when (it) {
            TextView.Gravity.Left -> Gravity.LEFT
            TextView.Gravity.Right -> Gravity.RIGHT
            TextView.Gravity.Top -> Gravity.TOP
            TextView.Gravity.Bottom -> Gravity.BOTTOM
        }
    }.reduce { acc, i -> acc or i }
    textView.gravity = gravity
    val textWatcher = dslText.onTextChange?.let {
        object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                it(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //empty
            }

        }
    }
    if (textWatcher != null) {
        textView.addTextChangedListener(textWatcher)
    }
    return object : Binding {
        override fun unbind() {
            viewBinding.unbind()
            if (textWatcher != null) {
                textView.removeTextChangedListener(textWatcher)
            }
        }
    }
}
