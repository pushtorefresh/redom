package com.pushtorefresh.redom.api

abstract class BaseComponent<DslView : com.pushtorefresh.redom.api.View, View : Any>(
    protected val dslView: DslView,
    val clazz: Class<out DslView>
) {

    private var _viewStructure: ViewStructure? = null

    val viewStructure: ViewStructure
        get() = _viewStructure ?: ViewStructure.create(this).also { _viewStructure = it }

    abstract fun bind(view: View): Binding
}

class Component<DslView : com.pushtorefresh.redom.api.View, View : Any>(
    private val binder: (DslView, View) -> Binding,
    dslView: DslView,
    clazz: Class<out DslView>
) : BaseComponent<DslView, View>(dslView, clazz) {

    override fun bind(view: View): Binding = binder(dslView, view)
}

class ComponentGroup<DslView : com.pushtorefresh.redom.api.ViewGroup, View : Any>(
    private val binder: (DslView, View, List<BaseComponent<*, *>>) -> Binding,
    dslView: DslView,
    clazz: Class<out DslView>,
    val children: List<BaseComponent<*, *>>
) : BaseComponent<DslView, View>(dslView, clazz) {

    override fun bind(view: View): Binding = binder(dslView, view, children)
}
