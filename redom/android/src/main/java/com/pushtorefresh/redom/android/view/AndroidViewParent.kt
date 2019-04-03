package com.pushtorefresh.redom.android.view

import com.pushtorefresh.redom.api.Button
import com.pushtorefresh.redom.api.CheckBox
import com.pushtorefresh.redom.api.EditText
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.Switch
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewParent

class AndroidViewParent<O : Any> : ViewParent<O> {
    override fun <V : View<O>> createView(clazz: Class<out V>): V {
        @Suppress("UNCHECKED_CAST")
        return when (clazz) {
            TextView::class.java -> TextViewImpl<O>() as V
            LinearLayout::class.java -> LinearLayoutImpl(this) as V
            Button::class.java -> ButtonImpl<O>() as V
            Switch::class.java -> SwitchImpl<O>() as V
            CheckBox::class.java -> CheckBoxImpl<O>() as V
            EditText::class.java -> EditTextImpl<O>() as V
            else -> TODO()
        }
    }
}
