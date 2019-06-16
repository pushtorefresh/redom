package com.pushtorefresh.redom.android.recycler

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Button
import com.pushtorefresh.redom.api.CheckBox
import com.pushtorefresh.redom.api.EditText
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.Switch
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewStructure

class Adapter(
    private val viewTypeRegistry: ViewTypeRegistry,
    private val inflater: (ViewStructure, parent: ViewGroup) -> android.view.View
) : RecyclerView.Adapter<ComponentViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var components: List<BaseComponent<out View, out Any>> = listOf()

    override fun onViewRecycled(holder: ComponentViewHolder) = holder.unbind()

    override fun getItemCount() = components.size

    override fun getItemViewType(position: Int) = viewTypeRegistry.viewTypeOf(components[position])

    override fun getItemId(position: Int): Long {
        return "$position-${getItemViewType(position)}".hashCode().toLong() // TODO revisit zalupa with Vova
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder =
        ComponentViewHolder(inflater(viewTypeRegistry.viewTreeOf(viewType), parent))

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        @Suppress("UNCHECKED_CAST")
        holder.bind(components[position] as BaseComponent<out View, Any>)
    }

    fun setComponents(components: List<BaseComponent<out View, out Any>>) {
        this.components = components
        notifyDataSetChanged()
    }
}

object Inflater : (ViewStructure, ViewGroup) -> android.view.View {
    override fun invoke(viewStructure: ViewStructure, parent: ViewGroup): android.view.View {
        return when (viewStructure) {
            is ViewStructure.View -> when (viewStructure.clazz) {
                TextView::class.java -> AppCompatTextView(parent.context)
                Button::class.java -> AppCompatButton(parent.context)
                Switch::class.java -> SwitchCompat(parent.context)
                CheckBox::class.java -> AppCompatCheckBox(parent.context)
                EditText::class.java -> AppCompatEditText(parent.context)
                else -> throw IllegalArgumentException("Inflating of ${viewStructure.clazz} is not supported yet")
            }
            is ViewStructure.ViewGroup -> when (viewStructure.clazz) {
                LinearLayout::class.java -> android.widget.LinearLayout(parent.context)
                else -> throw IllegalArgumentException("Inflating of ${viewStructure.clazz} is not supported yet")
            }.also { viewGroup: ViewGroup ->
                viewStructure.children.forEach { viewGroup.addView(invoke(it, parent)) }
            }
        }
    }
}
