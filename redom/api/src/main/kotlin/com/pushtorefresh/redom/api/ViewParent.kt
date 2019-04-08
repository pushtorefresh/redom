package com.pushtorefresh.redom.api

interface ViewParent<O> {
    fun <V : View<O>> createView(clazz: Class<out V>): V
}
