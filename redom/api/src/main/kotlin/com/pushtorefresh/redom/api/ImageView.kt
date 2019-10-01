package com.pushtorefresh.redom.api

interface ImageView : View {
    sealed class Drawable {
        abstract val scaleType: ScaleType?

        data class Resource(
            val resourceId: Int,
            override val scaleType: ScaleType? = null
        ) : Drawable()

        data class Http(
            val url: String,
            val placeholderId: Int? = null,
            override val scaleType: ScaleType? = null
        ) : Drawable()
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