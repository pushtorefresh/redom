package com.pushtorefresh.redom.android.recycler

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import com.pushtorefresh.redom.api.Button
import com.pushtorefresh.redom.api.CheckBox
import com.pushtorefresh.redom.api.EditText
import com.pushtorefresh.redom.api.LayoutParams
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.Switch
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.ViewStructure

class Inflater(
    private val lpFactory: (LayoutParams?) -> ViewGroup.LayoutParams?
) : (ViewStructure, ViewGroup) -> android.view.View {
    override fun invoke(viewStructure: ViewStructure, parent: ViewGroup): android.view.View {
        val context = parent.context
        val style = viewStructure.style

        return when (viewStructure) {
            is ViewStructure.View -> when (viewStructure.clazz) {
                TextView::class.java -> if (style == null) AppCompatTextView(context) else AppCompatTextView(context, null, style)
                Button::class.java -> if (style == null) AppCompatButton(context) else AppCompatButton(context, null, style)
                Switch::class.java -> if (style == null) SwitchCompat(context) else SwitchCompat(context, null, style)
                CheckBox::class.java -> if (style == null) AppCompatCheckBox(context) else AppCompatCheckBox(context, null, style)
                EditText::class.java -> if (style == null) AppCompatEditText(context) else AppCompatEditText(context, null, style)
                else -> throw IllegalArgumentException("Inflating of ${viewStructure.clazz} is not supported yet")
            }
            is ViewStructure.ViewGroup -> when (viewStructure.clazz) {
                LinearLayout::class.java -> if (style == null) android.widget.LinearLayout(context) else android.widget.LinearLayout(context, null, style)
                else -> throw IllegalArgumentException("Inflating of ${viewStructure.clazz} is not supported yet")
            }.also { viewGroup: ViewGroup ->
                viewStructure.children.forEach { viewGroup.addView(invoke(it, parent)) }
            }

        }.also { androidView ->
            lpFactory(viewStructure.layoutParams)?.also { lp ->
                androidView.layoutParams = lp
            }
        }
    }
}
