package com.pushtorefresh.redom.api

interface ComponentGroup<O: Any, V : Any> : Component<O, V> {
    val children: List<Component<O, out Any>>
}
