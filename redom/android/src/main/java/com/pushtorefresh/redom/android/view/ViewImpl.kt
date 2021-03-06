package com.pushtorefresh.redom.android.view

import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.ComponentContext
import com.pushtorefresh.redom.api.LayoutParams
import com.pushtorefresh.redom.api.View

abstract class ViewImpl : View {
    override var id: String? = null
    override var style: Int? = null
    override var layoutParams: LayoutParams? = null
    override var enabled: Boolean = true
    override var onClick: (() -> Unit)? = null
}

fun bindView(dslView: View, view: android.view.View, context: ComponentContext): Binding {
    view.id = context.registry.mapToInt(dslView.id)
    view.isEnabled = dslView.enabled
    val onClickListener = dslView.onClick?.let {
        android.view.View.OnClickListener { it() }
    }
    if (onClickListener != null) {
        view.setOnClickListener(onClickListener)
    }
    return object : Binding {
        override fun unbind() {
            if (onClickListener != null) {
                view.setOnClickListener(null)
            }
        }
    }
}
