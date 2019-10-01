package com.pushtorefresh.redom.api

interface Button : TextView {
    var background: ImageView.Drawable?
}

fun ViewParent.Button(lambda: Button.() -> Unit): Unit =
    lambda(createView(Button::class.java))
