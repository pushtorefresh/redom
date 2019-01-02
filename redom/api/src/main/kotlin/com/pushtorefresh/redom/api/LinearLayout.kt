package com.pushtorefresh.redom.api

interface LinearLayout<O> : ViewGroup<O, LinearLayout.Observe, LinearLayout.Change> {

    interface Observe : ViewGroup.Observe {

    }

    interface Change : ViewGroup.Change {

    }

    interface Init {
        var orientation: Orientation
    }

    enum class Orientation {
        Vertical,
        Horizontal,
    }

    fun init(lambda: Init.() -> Unit)
}

fun <O> Dom<O>.LinearLayout(lambda: LinearLayout<O>.() -> Unit): Unit = lambda(createComponent(LinearLayout::class.java) as LinearLayout<O>)
