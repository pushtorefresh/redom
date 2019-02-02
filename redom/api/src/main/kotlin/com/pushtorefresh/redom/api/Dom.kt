package com.pushtorefresh.redom.api

@Dom.RootDsl
interface Dom<O : Any> : ViewParent<O> {

    fun build(): List<Component<O, out Any>>

    @DslMarker
    annotation class RootDsl
}
