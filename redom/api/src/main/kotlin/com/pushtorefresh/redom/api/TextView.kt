// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api

@Dom.RootDsl
interface TextView<O : Any> : View<O> {
    var text: String
}

fun <O : Any> ViewParent<O>.TextView(lambda: TextView<O>.() -> Unit): Unit =
    lambda(createView(TextView::class.java as Class<TextView<O>>))
