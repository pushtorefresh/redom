package com.pushtorefresh.redom.api

@Dom.RootDsl
interface Dom : ViewParent {

    fun build(): List<BaseComponent<*, *>>

    @DslMarker
    annotation class RootDsl
}
