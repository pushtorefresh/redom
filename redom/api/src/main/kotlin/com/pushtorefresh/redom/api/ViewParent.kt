package com.pushtorefresh.redom.api

interface ViewParent {
    fun <V : View> createView(clazz: Class<out V>): V
}
