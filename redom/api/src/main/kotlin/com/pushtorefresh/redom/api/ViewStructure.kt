package com.pushtorefresh.redom.api

sealed class ViewStructure {
    data class View(
        val clazz: Class<out com.pushtorefresh.redom.api.View>
    ) : ViewStructure()

    data class ViewGroup(
        val clazz: Class<out com.pushtorefresh.redom.api.ViewGroup>,
        val children: List<ViewStructure>
    ) : ViewStructure()

    companion object {
        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun create(component: BaseComponent<*, *>): ViewStructure {
            return when (component) {
                is Component -> ViewStructure.View(component.clazz)
                is ComponentGroup -> ViewStructure.ViewGroup(
                    clazz = component.clazz as Class<out com.pushtorefresh.redom.api.ViewGroup>,
                    children = component.children.map { child -> create(child) }
                )
                else -> throw IllegalArgumentException("Can create viewStructure for type ${component.clazz}")
            }
        }
    }
}
