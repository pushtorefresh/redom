package com.pushtorefresh.redom.api

interface LinearLayout<O : Any> : ViewGroup<O> {

    var orientation: Orientation

    enum class Orientation {
        Vertical,
        Horizontal,
    }
}

fun <O : Any> ViewParent<O>.LinearLayout(lambda: LinearLayout<O>.() -> Unit): Unit =
    lambda(createView(LinearLayout::class.java as Class<LinearLayout<O>>))
