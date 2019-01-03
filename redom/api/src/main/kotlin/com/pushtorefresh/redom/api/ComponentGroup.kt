package com.pushtorefresh.redom.api

interface ComponentGroup<O> : Component<O> {
    val children: List<Component<O>>
}
