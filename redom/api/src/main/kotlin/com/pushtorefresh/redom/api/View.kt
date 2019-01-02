// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api

import io.reactivex.Observable

interface View<O, Ob : View.Observe, Ch : View.Change> {

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

    fun build(): Component
}
