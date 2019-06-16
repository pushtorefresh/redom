// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api

@Dom.RootDsl
interface TextView : View {
    var text: CharSequence
    var onTextChange: ((CharSequence) -> Unit)?
}

fun ViewParent.TextView(lambda: TextView.() -> Unit): Unit =
    lambda(createView(TextView::class.java))
