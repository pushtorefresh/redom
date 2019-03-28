package com.pushtorefresh.redom.api

sealed class ViewStructure {
    data class View(
            val clazz: Class<out com.pushtorefresh.redom.api.View<*>>
    ) : ViewStructure()

    data class ViewGroup(
            val clazz: Class<out com.pushtorefresh.redom.api.ViewGroup<*>>,
            val children: List<ViewStructure>
    ) : ViewStructure()
}

fun <O : Any, V : Any> toViewStructure(component: Component<O, V>): ViewStructure = when (component) {
    is ComponentGroup -> ViewStructure.ViewGroup(
            component.clazz,
            children = if (component.children.isEmpty()) {
                emptyList()
            } else {
                component.children.map { toViewStructure(it) }
            }
    )
    else -> ViewStructure.View(component.clazz)
}
