package com.pushtorefresh.redom.api

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface Component<O : Any, V : Any> {
    val clazz: Class<out View<*>>
    val output: Observable<O>
    val viewStructure: ViewStructure
    fun bind(view: V): Disposable
}
