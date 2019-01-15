package com.pushtorefresh.redom.android

import com.pushtorefresh.redom.android.view.TextViewImpl
import com.pushtorefresh.redom.api.ComponentGroup
import com.pushtorefresh.redom.api.Dom
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.View

internal class RecyclerDom<O> : Dom<O> {

    override fun build(): ComponentGroup<O> {
        return TODO()
    }

    override fun <Ob : View.Observe, Ch : View.Change, V : View<O, Ob, Ch>> createDsl(clazz: Class<out V>): V {
        return when (clazz) {
            TextView::class.java -> TextViewImpl<O>() as V

            else -> TODO()
        }
    }
}

fun <O> androidDom(builder: Dom<O>.() -> Unit): Dom<O> {
    return RecyclerDom<O>().apply(builder)
}

