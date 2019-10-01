package com.pushtorefresh.redom.android.view

import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.ComponentContext
import com.pushtorefresh.redom.api.EditText

class EditTextImpl : EditText, TextViewImpl() {

    override fun build(): BaseComponent<*, *> {
        return Component(
            binder = ::bindEditText,
            dslView = this,
            clazz = EditText::class.java
        )
    }
}

fun bindEditText(dslView: EditText, view: android.widget.EditText, componentContext: ComponentContext): Binding {
    return bindTextView(dslView, view, componentContext)
}
