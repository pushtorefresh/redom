package com.pushtorefresh.redom.api

import io.reactivex.Observable

interface Component {
    val clazz: Class<*>
    val observe: Map<String, Observable<*>>
    val change: Map<String, Observable<*>>
    // val immutable
}
