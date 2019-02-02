package com.pushtorefresh.redom.api

sealed class ViewStructure {
    data class View(
            val clazz: Class<out com.pushtorefresh.redom.api.View<*, *, *>>
    ) : ViewStructure()

    data class ViewGroup(
            val clazz: Class<out com.pushtorefresh.redom.api.ViewGroup<*, *, *>>,
            val children: List<ViewStructure>
    ) : ViewStructure()
}

fun <O : Any, V : Any> Component<O, V>.toViewStructure(): ViewStructure = when (this) {
    is ComponentGroup -> ViewStructure.ViewGroup(
            clazz,
            children = if (children.isEmpty()) {
                emptyList()
            } else {
                children.map { it.toViewStructure() }
            }
    )
    else -> ViewStructure.View(clazz)
}
