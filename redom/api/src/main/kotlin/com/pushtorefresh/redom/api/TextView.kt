// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api

import io.reactivex.Observable

@Dom.RootDsl
interface TextView<O : Any> : View<O> {
    var text: Observable<out CharSequence>
}

fun <O : Any> ViewParent<O>.TextView(lambda: TextView<O>.() -> Unit): Unit = lambda(createView(TextView::class.java as Class<TextView<O>>))
