package com.pushtorefresh.redom.android.recycler

import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewStructure

class ViewTypeRegistryImpl : ViewTypeRegistry {

    private var typeCount = 0
    private val registry = mutableMapOf<ViewStructure, Int>()

    override fun viewTypeOf(component: BaseComponent<out View, out Any>): Int {
        val viewTree = component.viewStructure

        return registry[viewTree] ?: typeCount.also {
            registry[viewTree] = typeCount
            typeCount += 1
        }
    }

    override fun viewTreeOf(viewType: Int): ViewStructure = registry
        .entries
        .first { (_, value) -> value == viewType }
        .key
}
