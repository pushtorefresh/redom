// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

interface View<O : Any, Ob : View.Observe, Ch : View.Change> {

    interface Observe {
        val clicks: Observable<Any>
    }

    interface Change {
    }

    interface Output<T> {
        operator fun plusAssign(observable: Observable<T>)
    }

    val observe: Ob
    val change: Ch
    val output: Output<O>

    fun observe(lambda: Ob.() -> Unit): Unit = lambda(observe)
    fun change(lambda: Ch.() -> Unit): Unit = lambda(change)

    fun build(): Component<O, out Any>
}

abstract class ViewImpl<O : Any, Ob : View.Observe, Ch : View.Change> : View<O, Ob, Ch> {

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

    protected open inner class ViewObserveImpl : View.Observe {
        override val clicks: Observable<Any>
            get() = observeClicks ?: PublishRelay.create<Any>().also {
                observeClicks = it
            }
    }
}
