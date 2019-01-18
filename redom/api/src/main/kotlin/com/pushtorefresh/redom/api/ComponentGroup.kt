package com.pushtorefresh.redom.api

import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable

interface ComponentGroup<O> : Component<O> {
    val children: List<Component<O>>

    private data class DComponentGroup<O>(
            override val clazz: Class<out View<*, *, *>>,
            override val output: Observable<O>,
            override val rawOutput: Observable<*>,
            override val initProperties: Map<String, *>,
            override val observeProperties: Map<String, Relay<*>>,
            override val changeProperties: Map<String, Observable<*>>,
            override val children: List<Component<O>>
    ) : ComponentGroup<O>

    companion object {
        fun <O> create(
                clazz: Class<out View<*, *, *>>,
                output: Observable<O>,
                rawOutput: Observable<*>,
                initProperties: Map<String, *>,
                observeProperties: Map<String, Relay<*>>,
                changeProperties: Map<String, Observable<*>>,
                children: List<Component<O>>
        ): ComponentGroup<O> = DComponentGroup(clazz, output, rawOutput, initProperties, observeProperties, changeProperties, children)
    }
}
