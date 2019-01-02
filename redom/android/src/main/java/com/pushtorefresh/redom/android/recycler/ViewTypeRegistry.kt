package com.pushtorefresh.redom.android.recycler

interface ViewTypeRegistry {
    fun viewTypeOf(clazz: Class<out Component>): Int
    fun componentClassOf(viewType: Int): Class<out Component>
}
