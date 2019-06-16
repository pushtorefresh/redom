package com.pushtorefresh.redom.android.recycler

import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewStructure

interface ViewTypeRegistry {
    fun viewTypeOf(component: BaseComponent<out View, out Any>): Int
    fun viewTreeOf(viewType: Int): ViewStructure
}
