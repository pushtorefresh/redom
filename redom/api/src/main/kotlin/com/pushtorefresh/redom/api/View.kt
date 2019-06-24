package com.pushtorefresh.redom.api

interface View {

    var enabled: Boolean
    var onClick: (() -> Unit)?
    var layoutParams: LayoutParams?
    var style: Int?

    fun build(): BaseComponent<out View, out Any>
}
