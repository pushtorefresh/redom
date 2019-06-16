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

        }.also { androidView ->
            lpFactory(viewStructure.layoutParams)?.also { lp ->
                androidView.layoutParams = lp
            }
        }
    }
}