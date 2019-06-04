// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

interface View<O : Any> {

    interface Output<T> {
        operator fun plusAssign(observable: Observable<T>)
    }

    var enabled: Observable<Boolean>
    val clicks: Observable<Any>
    val output: Output<O>

    fun build(): Component<O, out Any>
}

abstract class ViewImpl<O : Any> : View<O> {

    protected val outputObservables = mutableListOf<Observable<O>>()
    protected var observeClicks: PublishRelay<Any>? = null
        private set(value) {
            field = value
        }
    protected var changeEnabled: Observable<Boolean>? = null

    override val clicks: Observable<Any>
        get() = observeClicks ?: PublishRelay.create<Any>().also {
            observeClicks = it
        }

    override var enabled: Observable<Boolean>
        get() = throw IllegalAccessError("Enabled cannot be observed")
        set(value) {
            changeEnabled= value
        }

    override val output = object : View.Output<O> {
        override fun plusAssign(observable: Observable<O>) {
            outputObservables += observable
        }
    }
}
