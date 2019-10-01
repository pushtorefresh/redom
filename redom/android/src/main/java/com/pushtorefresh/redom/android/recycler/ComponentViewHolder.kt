package com.pushtorefresh.redom.android.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.IdRegistry

class ComponentViewHolder(view: View, private val idRegistry: IdRegistry<String>) : RecyclerView.ViewHolder(view) {
    private val bindings = mutableListOf<Binding>()

    fun bind(component: BaseComponent<out com.pushtorefresh.redom.api.View, Any>) {
        unbind()
        bindings += component.bind(itemView, idRegistry)
    }

    fun unbind() {
        bindings.forEach { it.unbind() }
        bindings.clear()
    }
}
