// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api

import io.reactivex.Observable

@Dom.RootDsl
interface TextView<O> : View<O, TextView.Observe, TextView.Change> {
    interface Observe : View.Observe {
        var textChanges: Observable<out CharSequence>
    }

    interface Change : View.Change {
        var text: Observable<out CharSequence>
    }
}

fun <O> Dom<O>.TextView(lambda: TextView<O>.() -> Unit): Unit = lambda(createComponent(TextView::class.java) as TextView<O>)
