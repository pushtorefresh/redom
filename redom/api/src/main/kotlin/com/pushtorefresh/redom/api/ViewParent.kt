package com.pushtorefresh.redom.api

interface ViewParent<O> {
    fun <Ob: View.Observe, Ch: View.Change, V: View<O, Ob, Ch>> createView(clazz: Class<out V>): V
}
