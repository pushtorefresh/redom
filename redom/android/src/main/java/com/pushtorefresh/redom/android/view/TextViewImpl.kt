package com.pushtorefresh.redom.android.view

import android.text.Editable
import android.text.TextWatcher
import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.TextView

open class TextViewImpl : TextView, ViewImpl() {
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
