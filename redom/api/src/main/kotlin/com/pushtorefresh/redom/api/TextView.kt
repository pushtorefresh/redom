// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api

import io.reactivex.Observable

@Dom.RootDsl
interface TextView<O> : View<O, TextView.Observe, TextView.Change> {
    interface Observe : View.Observe {
        val textChanges: Observable<out CharSequence>
    }

    interface Change : View.Change {
        var text: Observable<out CharSequence>
    }
}

fun <O> ViewParent<O>.TextView(lambda: TextView<O>.() -> Unit): Unit = lambda(createView(TextView::class.java as Class<TextView<O>>))
