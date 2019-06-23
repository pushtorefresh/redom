package com.pushtorefresh.redom.api

interface View {

    var enabled: Boolean
    var onClick: (() -> Unit)?
    var layoutParams: LayoutParams?

    fun build(): BaseComponent<out View, out Any>
}