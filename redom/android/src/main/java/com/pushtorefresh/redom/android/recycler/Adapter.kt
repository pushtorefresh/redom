package com.pushtorefresh.redom.android.recycler

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.pushtorefresh.redom.api.*

class Adapter(
        private val viewTypeRegistry: ViewTypeRegistry,
        private val inflater: (Class<out View<*, *, *>>, parent: ViewGroup) -> RecyclerView.ViewHolder
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var components: List<Component<*>> = listOf()

    override fun getItemCount() = components.size

    override fun getItemViewType(position: Int) = viewTypeRegistry.viewTypeOf(components[position].clazz)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return inflater(viewTypeRegistry.componentClassOf(viewType), parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val component = components[position]

        when (component.clazz) {
            TextView::class.java -> (holder as TextViewViewHolder).bind(component)
            LinearLayout::class.java -> (holder as LinearLayoutViewHolder).bind(component as ComponentGroup<*>)
        }

        if (component is ComponentGroup) {
            component
                    .children
                    .forEach { child ->
                        val parent = holder as LinearLayoutViewHolder
                        val horder = inflater(child.clazz, holder.itemView as ViewGroup)
                        (parent.itemView as ViewGroup).addView(horder.itemView)
                        (horder as TextViewViewHolder).bind(child)
                    }
        }
    }

    fun setComponents(components: List<Component<*>>) {
        this.components = components
        notifyDataSetChanged()
    }
}

object Inflater : (Class<out View<*, *, *>>, ViewGroup) -> RecyclerView.ViewHolder {
    override fun invoke(viewClass: Class<out View<*, *, *>>, parent: ViewGroup): RecyclerView.ViewHolder {
        return when (viewClass) {
            TextView::class.java -> TextViewViewHolder(AppCompatTextView(parent.context))
            LinearLayout::class.java -> LinearLayoutViewHolder(android.widget.LinearLayout(parent.context))
            else -> TODO("Not implemented for $viewClass")
        }
    }
}
