package com.pushtorefresh.redom.api

interface ComponentGroup<O: Any, V : Any> : Component<O, V> {
    override val clazz: Class<out ViewGroup<*>>
    val children: List<Component<O, out Any>>
}
