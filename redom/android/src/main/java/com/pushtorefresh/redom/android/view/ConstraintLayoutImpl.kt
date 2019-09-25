package com.pushtorefresh.redom.android.view

import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.ComponentGroup
import com.pushtorefresh.redom.api.ConstraintLayout
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewParent

class ConstraintLayoutImpl(private val viewParent: ViewParent) : ConstraintLayout, ViewImpl() {

    private val views = mutableListOf<View>()

    override fun <V : View> createView(clazz: Class<out V>): V {
        return viewParent.createView(clazz).also { views += it }
    }

    override fun build(): BaseComponent<out View, out Any> {
        val children = views.map { it.build() }
        return ComponentGroup(
            binder = ::bindConstraintLayout,
            dslView = this,
            clazz = ConstraintLayout::class.java,
            children = children
        )
    }
}

fun bindConstraintLayout(
    dslView: ConstraintLayout,
    view: androidx.constraintlayout.widget.ConstraintLayout,
    children: List<BaseComponent<*, *>>
): Binding {
    val bindView = bindView(dslView, view)
    val childrenBindings = children.mapIndexed { index, childComponent ->
        @Suppress("UNCHECKED_CAST")
        (childComponent as BaseComponent<View, android.view.View>).bind(view.getChildAt(index))
    }
    return object: Binding {
        override fun unbind() {
            childrenBindings.forEach(Binding::unbind)
            bindView.unbind()
        }
    }
}
