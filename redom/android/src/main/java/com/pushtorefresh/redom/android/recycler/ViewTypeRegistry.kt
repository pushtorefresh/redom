package com.pushtorefresh.redom.android.recycler

import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.ViewStructure

interface ViewTypeRegistry {
    fun viewTypeOf(component: Component<out Any, out Any>): Int
    fun viewTreeOf(viewType: Int): ViewStructure
}
