package com.pushtorefresh.redom.android.view

import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.Button
import com.pushtorefresh.redom.api.Component
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

fun bindButton(dslView: Button, view: android.widget.Button): Binding {
    val bindTextView = bindTextView(dslView, view)
    val imageFuture = dslView.background?.let { drawable ->
        Glide.with(view)
            .createImageRequest(drawable)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // empty
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    view.background = resource
                    return true
                }
            })
            .submit()
    }
    return object : Binding {
        override fun unbind() {
            imageFuture?.cancel(true)
            bindTextView.unbind()
        }
    }
}
