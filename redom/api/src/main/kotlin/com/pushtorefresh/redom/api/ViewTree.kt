package com.pushtorefresh.redom.api

sealed class ViewTree {
    data class View(
            val clazz: Class<out com.pushtorefresh.redom.api.View<*, *, *>>
    ) : ViewTree()

    data class ViewGroup(
            val clazz: Class<out com.pushtorefresh.redom.api.ViewGroup<*, *, *>>,
            val children: List<ViewTree>
    ) : ViewTree()
}

fun <O : Any, V : Any> Component<O, V>.toViewTree(): ViewTree = when (this) {
    is ComponentGroup -> ViewTree.ViewGroup(
            clazz,
            children = if (children.isEmpty()) {
                emptyList()
            } else {
                children.map { it.toViewTree() }
            }
    )
    else -> ViewTree.View(clazz)
}
