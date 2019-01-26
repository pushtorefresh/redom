package com.pushtorefresh.redom.android.recycler

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.ComponentGroup
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.View

class Adapter(
        private val viewTypeRegistry: ViewTypeRegistry,
        private val inflater: (Class<out View<*, *, *>>, parent: ViewGroup) -> ComponentViewHolder
) : RecyclerView.Adapter<ComponentViewHolder>() {

    private var components: List<Component<out Any, out Any>> = listOf()

    override fun getItemCount() = components.size

    override fun getItemViewType(position: Int) = viewTypeRegistry.viewTypeOf(components[position].clazz)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        return inflater(viewTypeRegistry.componentClassOf(viewType), parent)
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        val component = components[position]
        @Suppress("UNCHECKED_CAST")
        holder.bind(component as Component<out Any, Any>)
        if (component is ComponentGroup) {
            component
                    .children
                    .forEach { child ->
                        val childHolder = inflater(child.clazz, holder.itemView as ViewGroup)
                        holder.itemView.addView(childHolder.itemView)
                        @Suppress("UNCHECKED_CAST")
                        childHolder.bind(child as Component<out Any, Any>)
                        // TODO move to onCreateViewHolder
                    }
        }
    }

    fun setComponents(components: List<Component<out Any,out Any>>) {
        this.components = components
        notifyDataSetChanged()
    }
}

object Inflater : (Class<out View<*, *, *>>, ViewGroup) -> ComponentViewHolder {
    override fun invoke(viewClass: Class<out View<*, *, *>>, parent: ViewGroup): ComponentViewHolder {
        return when (viewClass) {
            TextView::class.java -> ComponentViewHolder(AppCompatTextView(parent.context))
            LinearLayout::class.java -> ComponentViewHolder(android.widget.LinearLayout(parent.context))
            else -> TODO("Not implemented for $viewClass")
        }
    }
}
