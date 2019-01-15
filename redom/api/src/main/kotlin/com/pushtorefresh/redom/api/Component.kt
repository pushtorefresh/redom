package com.pushtorefresh.redom.api

import io.reactivex.Observable

interface Component<O> {
    val clazz: Class<out View<*, *, *>>
    val output: Observable<O>
    val rawOutput: Observable<*>

    val initProperties: Map<String, *>
    val observeProperties: Map<String, Observable<*>>
    val changeProperties: Map<String, Observable<*>>

    fun <T> getChangeProperty(name: String): Observable<T> = changeProperties[name] as Observable<T>

    private data class DComponent<O>(
            override val clazz: Class<out View<*, *, *>>,
            override val output: Observable<O>,
            override val rawOutput: Observable<*>,
            override val initProperties: Map<String, *>,
            override val observeProperties: Map<String, Observable<*>>,
            override val changeProperties: Map<String, Observable<*>>
    ) : Component<O>

    companion object {
        fun <O> create(
                clazz: Class<out View<*, *, *>>,
                output: Observable<O>,
                rawOutput: Observable<*>,
                initProperties: Map<String, *>,
                observeProperties: Map<String, Observable<*>>,
                changeProperties: Map<String, Observable<*>>
        ): Component<O> = DComponent(clazz, output, rawOutput, initProperties, observeProperties, changeProperties)
    }

}
