package com.pushtorefresh.redom.android.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.View

class Adapter(
        private val viewTypeRegistry: ViewTypeRegistry,
        private val inflator: (Class<out View<*, *, *>>, parent: ViewGroup) -> RecyclerView.ViewHolder
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var components: List<Component<*>> = listOf()

    override fun getItemCount() = components.size

    override fun getItemViewType(position: Int) = viewTypeRegistry.viewTypeOf(components[position].clazz)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return inflator(viewTypeRegistry.componentClassOf(viewType), parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TextViewViewHolder).bind(components[position]) // TODO
    }

    fun setComponents(components: List<Component<*>>) {
        this.components = components
        notifyDataSetChanged()
    }
}

object Inflator : (Class<out View<*, *, *>>, ViewGroup) -> RecyclerView.ViewHolder {
    override fun invoke(viewClass: Class<out View<*, *, *>>, parent: ViewGroup): RecyclerView.ViewHolder {
        return when (viewClass) {
            TextView::class.java -> TextViewViewHolder(android.widget.TextView(parent.context))
            else -> TODO()
        }
    }
}
