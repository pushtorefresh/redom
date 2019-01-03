package com.pushtorefresh.redom.api

import io.reactivex.Observable

interface Component<O> {
    val clazz: Class<*>
    val output: Observable<O>
    val rawOutput: Observable<*>

    val initProperties: Map<String, *>
    val observeProperties: Map<String, Observable<*>>
    val changeProperties: Map<String, Observable<*>>
}
