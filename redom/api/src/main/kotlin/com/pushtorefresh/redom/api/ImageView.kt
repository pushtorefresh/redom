package com.pushtorefresh.redom.api

interface ImageView : View {
}

@Suppress("UNCHECKED_CAST")
fun ViewParent.ImageView(lambda: ImageView.() -> Unit): Unit =
    lambda(createView(ImageView::class.java))