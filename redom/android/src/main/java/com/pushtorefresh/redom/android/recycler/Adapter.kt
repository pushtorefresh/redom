package com.pushtorefresh.redom.android.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class Adapter(
        private val viewTypeRegistry: ViewTypeRegistry,
        private val inflator: (Class<out Component>, parent: ViewGroup?) -> View
) : RecyclerView.Adapter<RecyclerViewViewHolder>() {

    private val components: List<Component> = listOf()

    override fun getItemCount() = components.size

    override fun getItemViewType(position: Int) = viewTypeRegistry.viewTypeOf(components[position]::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val view = inflator(viewTypeRegistry.componentClassOf(viewType), parent)
        return RecyclerViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        components[position].bind(holder.itemView)
    }
}
