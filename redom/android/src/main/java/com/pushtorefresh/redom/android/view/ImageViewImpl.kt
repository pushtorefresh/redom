package com.pushtorefresh.redom.android.view

import android.graphics.drawable.Drawable
import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.ComponentContext
import com.pushtorefresh.redom.api.ImageView

class ImageViewImpl : ImageView, ViewImpl() {
    // TODO strange why nullable
    override var drawable: ImageView.Drawable? = null

    override fun build(): BaseComponent<*, *> {
        return Component(
            binder = ::bindImageView,
            dslView = this,
            clazz = ImageView::class.java
        )
    }

}

fun bindImageView(
    dslImageView: ImageView,
    imageView: android.widget.ImageView,
    context: ComponentContext
): Binding {
    val binding = bindView(dslImageView, imageView, context)
    val imageLoader = context.getImageLoader<Drawable>()
    val request = dslImageView.drawable?.let { drawable ->
        imageLoader.load(drawable) { result ->
            imageView.setImageDrawable(result)
        }
    }

    return object : Binding {
        override fun unbind() {
            request?.close()
            binding.unbind()
        }
    }
}