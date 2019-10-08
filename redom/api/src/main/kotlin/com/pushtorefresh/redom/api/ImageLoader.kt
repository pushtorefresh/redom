package com.pushtorefresh.redom.api

import java.io.Closeable

interface ImageLoader<Target> {
    fun load(drawable: ImageView.Drawable, listener: (Target) -> Unit): Closeable
}