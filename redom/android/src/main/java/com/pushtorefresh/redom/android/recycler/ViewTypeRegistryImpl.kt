package com.pushtorefresh.redom.android.recycler

import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.ViewTree
import com.pushtorefresh.redom.api.toViewTree

class ViewTypeRegistryImpl : ViewTypeRegistry {

    private var typeCount = 0
    private val registry = mutableMapOf<ViewTree, Int>()

    override fun viewTypeOf(component: Component<out Any, out Any>): Int {
        val viewTree = component.toViewTree() // TODO add cache for ViewTree calculation.

        return registry[viewTree] ?: typeCount.also {
            registry[viewTree] = typeCount
            typeCount += 1
        }
    }

    override fun viewTreeOf(viewType: Int): ViewTree = registry
            .entries
            .first { (_, value) -> value == viewType }
            .key
}
