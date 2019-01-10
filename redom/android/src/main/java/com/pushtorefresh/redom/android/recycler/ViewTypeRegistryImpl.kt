package com.pushtorefresh.redom.android.recycler

import com.pushtorefresh.redom.api.View

class ViewTypeRegistryImpl : ViewTypeRegistry {
    private var typeCount = 0
    private val registry = mutableMapOf<Class<out View<*, *, *>>, Int>()

    override fun viewTypeOf(clazz: Class<out View<*, *, *>>): Int {
        return registry[clazz] ?: (typeCount++).apply {
            registry[clazz] = this
        }
    }

    override fun componentClassOf(viewType: Int): Class<out View<*, *, *>> {
        return registry.entries.first { (_, value) ->  value == viewType}.key
    }
}