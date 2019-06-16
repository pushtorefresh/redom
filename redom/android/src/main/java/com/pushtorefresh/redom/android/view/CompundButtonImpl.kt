package com.pushtorefresh.redom.android.view

import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Binding
import com.pushtorefresh.redom.api.CheckBox
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.CompoundButton
import com.pushtorefresh.redom.api.Switch

abstract class CompundButtonImpl : ButtonImpl(), CompoundButton {

    override var checked: Boolean = false
    override var onCheckedChange: ((Boolean) -> Unit)? = null

    override fun build(): BaseComponent<*, *> {
        return Component(
            binder = ::bindCompoundButton,
            dslView = this,
            clazz = getCompoundClass()
        )
    }

    abstract fun getCompoundClass(): Class<out CompoundButton>
}

fun bindCompoundButton(dslView: CompoundButton, view: android.widget.CompoundButton): Binding {
    val bindButton = bindButton(dslView, view)
    view.isChecked = dslView.checked

    val onCheckedChangeListener = dslView.onCheckedChange?.let {
        android.widget.CompoundButton.OnCheckedChangeListener { _, isChecked ->
            it(isChecked)
        }
    }
    if (onCheckedChangeListener != null) {
        view.setOnCheckedChangeListener(onCheckedChangeListener)
    }
    return object : Binding {
        override fun unbind() {
            bindButton.unbind()
            if (onCheckedChangeListener != null) {
                view.setOnCheckedChangeListener(null)
            }
        }
    }
}

class SwitchImpl : CompundButtonImpl(), Switch {
    override fun getCompoundClass() = Switch::class.java
}

class CheckBoxImpl : CompundButtonImpl(), CheckBox {
    override fun getCompoundClass() = CheckBox::class.java
}
