package com.pushtorefresh.redom.android.view

import android.graphics.drawable.Drawable
import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.Button
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.ComponentContext
import com.pushtorefresh.redom.api.ImageView

open class ButtonImpl : Button, TextViewImpl() {
    override var background: ImageView.Drawable? = null

    override fun build(): BaseComponent<*, *> {
        return Component(
            binder = ::bindButton,
            dslView = this,
            clazz = Button::class.java
        )
    }
}

fun bindButton(dslView: Button, view: android.widget.Button, componentContext: ComponentContext): Binding {
    val bindTextView = bindTextView(dslView, view, componentContext)
    val loader = componentContext.getImageLoader<Drawable>()
    val imageFuture = dslView.background?.let { drawable ->
        loader.load(drawable) { loaded ->
            view.background = loaded
        }
    }
    return object : Binding {
        override fun unbind() {
            imageFuture?.close()
            bindTextView.unbind()
        }
    }
}
