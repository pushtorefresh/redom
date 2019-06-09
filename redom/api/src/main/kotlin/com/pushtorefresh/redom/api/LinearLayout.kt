package com.pushtorefresh.redom.api

interface LinearLayout : ViewGroup {

    var orientation: Orientation

    enum class Orientation {
        Vertical,
        Horizontal,
    }
}

fun  ViewParent.LinearLayout(lambda: LinearLayout.() -> Unit): Unit =
    lambda(createView(LinearLayout::class.java))
