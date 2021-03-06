package com.pushtorefresh.redom.api

sealed class ViewStructure {

    abstract val clazz: Class<out com.pushtorefresh.redom.api.View>
    abstract val style: Int?
    abstract val layoutParams: LayoutParams?

    data class View(
        override val clazz: Class<out com.pushtorefresh.redom.api.View>,
        override val style: Int?,
        override val layoutParams: LayoutParams?
    ) : ViewStructure()

    data class ViewGroup(
        override val clazz: Class<out com.pushtorefresh.redom.api.ViewGroup>,
        val children: List<ViewStructure>,
        override val style: Int?,
        override val layoutParams: LayoutParams?
    ) : ViewStructure()
}
