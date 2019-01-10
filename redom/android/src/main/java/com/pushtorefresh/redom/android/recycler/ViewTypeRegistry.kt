package com.pushtorefresh.redom.android.recycler

import com.pushtorefresh.redom.api.View


interface ViewTypeRegistry {
    fun viewTypeOf(clazz: Class<out View<*, *, *>>): Int
    fun componentClassOf(viewType: Int): Class<out View<*, *, *>>
}
