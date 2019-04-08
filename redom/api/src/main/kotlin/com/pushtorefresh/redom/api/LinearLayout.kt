package com.pushtorefresh.redom.api

import io.reactivex.Observable

interface LinearLayout<O : Any> : ViewGroup<O> {

    var orientation: Observable<Orientation>

    enum class Orientation {
        Vertical,
        Horizontal,
    }
}

fun <O : Any> ViewParent<O>.LinearLayout(lambda: LinearLayout<O>.() -> Unit): Unit =
    lambda(createView(LinearLayout::class.java as Class<LinearLayout<O>>))
