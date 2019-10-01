package com.pushtorefresh.redom.android.view

import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.EditText
import com.pushtorefresh.redom.api.IdRegistry

class EditTextImpl : EditText, TextViewImpl() {

    override fun build(): BaseComponent<*, *> {
        return Component(
            binder = ::bindEditText,
            dslView = this,
            clazz = EditText::class.java
        )
    }
}

fun bindEditText(dslView: EditText, view: android.widget.EditText, idRegistry: IdRegistry<String>): Binding {
    return bindTextView(dslView, view, idRegistry)
}
