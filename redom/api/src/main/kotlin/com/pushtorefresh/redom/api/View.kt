// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

interface View<O : Any> {

    interface Output<T> {
        operator fun plusAssign(observable: Observable<T>)
    }

    var events: (ViewEvent) -> Unit
    val output: Output<O>

    fun build(): Component<O, out Any>
}

abstract class ViewImpl<O : Any> : View<O> {

    protected val outputObservables = mutableListOf<Observable<O>>()
    protected var observeClicks: PublishRelay<Any>? = null
        private set(value) {
            field = value
        }

    override val output = object : View.Output<O> {
        override fun plusAssign(observable: Observable<O>) {
            outputObservables += observable
        }
    }
}
