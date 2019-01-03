package com.pushtorefresh.redom.api

@Dom.RootDsl
interface Dom<O> {

    fun <Ob: View.Observe, Ch: View.Change, V: View<O, Ob, Ch>> createDsl(clazz: Class<out V>): V

    fun build(): ComponentGroup<O>

    @DslMarker
    annotation class RootDsl
}
