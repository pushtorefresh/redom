package com.pushtorefresh.redom.android.view

import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.ComponentGroup
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.LinearLayout.Orientation.Horizontal
import com.pushtorefresh.redom.api.LinearLayout.Orientation.Vertical
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewParent

class LinearLayoutImpl(private val viewParent: ViewParent) : LinearLayout, ViewImpl() {
    override var orientation = LinearLayout.Orientation.Vertical

    val views = mutableListOf<View>()

    override fun <V : View> createView(clazz: Class<out V>): V {
        return viewParent.createView(clazz).also { views += it }
    }

    override fun build(): BaseComponent<*, *> {
        val children = views.map { it.build() }
        return ComponentGroup(
            binder = ::bindLinearLayout,
            dslView = this,
            clazz = LinearLayout::class.java,
            children = children
        )
    }
}

fun bindLinearLayout(
    dslView: LinearLayout,
    view: android.widget.LinearLayout,
    children: List<BaseComponent<*, *>>
): Binding {
    val bindView = bindView(dslView, view)
    view.orientation = when (dslView.orientation) {
        Horizontal -> android.widget.LinearLayout.HORIZONTAL
        Vertical -> android.widget.LinearLayout.VERTICAL
    }
    val childrenBindings = children.mapIndexed { index, childComponent ->
        @Suppress("UNCHECKED_CAST")
        (childComponent as BaseComponent<View, android.view.View>).bind(view.getChildAt(index))
    }
    return object : Binding {
        override fun unbind() {
            childrenBindings.forEach(Binding::unbind)
            bindView.unbind()
        }
    }
}
