package com.pushtorefresh.redom.api

import io.reactivex.Observable

@Dom.RootDsl
interface Dom<O> {
    val output: Observable<O>
    fun <T> createComponent(clazz: Class<T>): T

    fun build(): List<Component>

    @DslMarker
    annotation class RootDsl
}
