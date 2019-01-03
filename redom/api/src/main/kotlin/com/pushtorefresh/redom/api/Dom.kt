package com.pushtorefresh.redom.api

@Dom.RootDsl
interface Dom<O> {

    fun <T> createComponent(clazz: Class<T>): T

    fun build(): List<Component>

    @DslMarker
    annotation class RootDsl
}
