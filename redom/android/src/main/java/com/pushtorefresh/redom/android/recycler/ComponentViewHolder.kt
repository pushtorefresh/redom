package com.pushtorefresh.redom.android.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding

class ComponentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val bindings = mutableListOf<Binding>()

    fun bind(component: BaseComponent<out com.pushtorefresh.redom.api.View, Any>) {
        unbind()
        bindings += component.bind(itemView)
    }

    fun unbind() {
        bindings.forEach { it.unbind() }
        bindings.clear()
    }
}
