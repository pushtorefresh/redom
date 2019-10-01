package com.pushtorefresh.redom.api

abstract class BaseComponent<DslView : com.pushtorefresh.redom.api.View, View : Any>(
    protected val dslView: DslView,
    val clazz: Class<out DslView>
) {
    abstract val viewStructure: ViewStructure

    abstract fun bind(view: View, idRegistry: IdRegistry<String>): Binding
}

class Component<DslView : com.pushtorefresh.redom.api.View, View : Any>(
    private val binder: (DslView, View, IdRegistry<String>) -> Binding,
    dslView: DslView,
    clazz: Class<out DslView>
) : BaseComponent<DslView, View>(dslView, clazz) {

    override val viewStructure = ViewStructure.View(clazz, dslView.style, dslView.layoutParams)

    override fun bind(view: View, idRegistry: IdRegistry<String>): Binding = binder(dslView, view, idRegistry)
}

class ComponentGroup<DslView : com.pushtorefresh.redom.api.ViewGroup, View : Any>(
    private val binder: (DslView, View, IdRegistry<String>, List<BaseComponent<*, *>>) -> Binding,
    dslView: DslView,
    clazz: Class<out DslView>,
    val children: List<BaseComponent<*, *>>
) : BaseComponent<DslView, View>(dslView, clazz) {

    override val viewStructure = ViewStructure.ViewGroup(
        clazz,
        children.map { it.viewStructure },
        dslView.style,
        dslView.layoutParams
    )

    override fun bind(view: View, idRegistry: IdRegistry<String>): Binding = binder(dslView, view, idRegistry, children)
}
