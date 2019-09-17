package com.pushtorefresh.redom.android.view

import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.ImageView

class ImageViewImpl : ImageView, ViewImpl() {

    override fun build(): BaseComponent<*, *> {
        return Component(
            binder = ::bindImageView,
            dslView = this,
            clazz = ImageView::class.java
        )
    }

}

fun bindImageView(dslImageView: ImageView, imageView: android.widget.ImageView): Binding {
    val binding = bindView(dslImageView, imageView)

    return object : Binding {
        override fun unbind() {
            binding.unbind()
        }
    }
}