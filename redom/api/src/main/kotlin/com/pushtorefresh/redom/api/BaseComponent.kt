package com.pushtorefresh.redom.api

class ComponentContext(
    val registry: IdRegistry<String>,
    private val imageLoader: ImageLoader<*>
) {
    @Suppress("UNCHECKED_CAST")
    fun <T> getImageLoader(): ImageLoader<T> = imageLoader as ImageLoader<T>
}

abstract class BaseComponent<DslView : com.pushtorefresh.redom.api.View, View : Any>(
    protected val dslView: DslView,
    val clazz: Class<out DslView>
) {
    abstract val viewStructure: ViewStructure

    abstract fun bind(view: View, context: ComponentContext): Binding
}

class Component<DslView : com.pushtorefresh.redom.api.View, View : Any>(
    private val binder: (DslView, View, ComponentContext) -> Binding,
    dslView: DslView,
    clazz: Class<out DslView>
) : BaseComponent<DslView, View>(dslView, clazz) {

    override val viewStructure = ViewStructure.View(clazz, dslView.style, dslView.layoutParams)

    override fun bind(view: View, context: ComponentContext): Binding = binder(dslView, view, context)
}

class ComponentGroup<DslView : com.pushtorefresh.redom.api.ViewGroup, View : Any>(
    private val binder: (DslView, View, ComponentContext, List<BaseComponent<*, *>>) -> Binding,
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

    override fun bind(view: View, context: ComponentContext): Binding = binder(dslView, view, context, children)
}


