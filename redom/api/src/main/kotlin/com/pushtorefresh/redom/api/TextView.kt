// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api

import java.util.*

@Dom.RootDsl
interface TextView : View {
    enum class Gravity {
        Top,
        Bottom,
        Right,
        Left,
        CenterVertical,
        CenterHorizontal,
        Center
    }
    var text: CharSequence
    var gravity: EnumSet<Gravity>
    var onTextChange: ((CharSequence) -> Unit)?
}

fun ViewParent.TextView(lambda: TextView.() -> Unit): Unit =
    lambda(createView(TextView::class.java))
