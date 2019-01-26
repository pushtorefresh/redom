package com.pushtorefresh.redom.api

@Dom.RootDsl
interface Dom<O : Any> : ViewParent<O> {

    fun build(): ComponentGroup<O, *>

    @DslMarker
    annotation class RootDsl
}
