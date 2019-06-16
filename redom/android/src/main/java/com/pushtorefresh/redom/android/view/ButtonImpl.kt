package com.pushtorefresh.redom.android.view

import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.Button
import com.pushtorefresh.redom.api.Component

open class ButtonImpl : Button, TextViewImpl() {

    override fun build(): BaseComponent<*, *> {
        return Component(
            binder = ::bindButton,
            dslView = this,
            clazz = Button::class.java
        )
    }
}

fun bindButton(dslView: Button, view: android.widget.Button): Binding {
    return bindTextView(dslView, view)
}
