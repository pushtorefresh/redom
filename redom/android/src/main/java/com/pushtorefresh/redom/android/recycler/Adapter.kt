package com.pushtorefresh.redom.android.recycler

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.ComponentContext
import com.pushtorefresh.redom.api.IdRegistry
import com.pushtorefresh.redom.api.ImageLoader
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewStructure

class Adapter(
    private val viewTypeRegistry: ViewTypeRegistry,
    private val inflater: (ViewStructure, parent: ViewGroup) -> android.view.View,
    idRegistry: IdRegistry<String>,
    imageLoader: ImageLoader<Drawable>
) : RecyclerView.Adapter<ComponentViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private val context = ComponentContext(idRegistry, imageLoader)

    private var components: List<BaseComponent<out View, out Any>> = listOf()

    override fun onViewRecycled(holder: ComponentViewHolder) = holder.unbind()

    override fun getItemCount() = components.size

    override fun getItemViewType(position: Int) = viewTypeRegistry.viewTypeOf(components[position])

    override fun getItemId(position: Int): Long {
        return "$position-${getItemViewType(position)}".hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder =
        ComponentViewHolder(inflater(viewTypeRegistry.viewTreeOf(viewType), parent), context)

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        @Suppress("UNCHECKED_CAST")
        holder.bind(components[position] as BaseComponent<out View, Any>)
    }

    fun setComponents(components: List<BaseComponent<out View, out Any>>) {
        this.components = components
        notifyDataSetChanged()
    }
}
