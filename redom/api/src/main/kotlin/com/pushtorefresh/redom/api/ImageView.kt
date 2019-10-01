package com.pushtorefresh.redom.api

interface ImageView : View {
    sealed class Drawable(val scaleType: ScaleType? = null) {
        class Resource(
            val resourceId: Int,
            scaleType: ScaleType? = null
        ) : Drawable(scaleType)

        class Http(
            val url: String,
            val placeholderId: Int? = null,
            scaleType: ScaleType? = null
        ) : Drawable(scaleType)
    }

    sealed class ScaleType {
        object CenterCrop : ScaleType()
        object CenterInside : ScaleType()
        object FitCenter : ScaleType()
    }

    var drawable: Drawable?
}

@Suppress("UNCHECKED_CAST")
fun ViewParent.ImageView(lambda: ImageView.() -> Unit): Unit =
    lambda(createView(ImageView::class.java))