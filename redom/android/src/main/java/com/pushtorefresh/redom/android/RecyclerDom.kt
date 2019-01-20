package com.pushtorefresh.redom.android

import com.pushtorefresh.redom.android.view.TextViewImpl
import com.pushtorefresh.redom.api.ComponentGroup
import com.pushtorefresh.redom.api.Dom
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.View
import io.reactivex.Observable

internal class RecyclerDom<O> : Dom<O> {
    private val views = mutableListOf<View<O, *, *>>()

    override fun build(): ComponentGroup<O> {
        val childComponents = views.map(View<O, *, *>::build)
        return ComponentGroup.create(
            LinearLayout::class.java,
            Observable.empty(),
            Observable.empty<Any>(),
            emptyMap<String, Any>(),
            emptyMap(),
            emptyMap(),
            childComponents
        )
    }

    override fun <Ob : View.Observe, Ch : View.Change, V : View<O, Ob, Ch>> createDsl(clazz: Class<out V>): V {
        @Suppress("UNCHECKED_CAST")
        val view = when (clazz) {
            TextView::class.java -> TextViewImpl<O>() as V
            else -> TODO()
        }
        views += view
        return view
    }
}

fun <O> androidDom(builder: Dom<O>.() -> Unit): Dom<O> {
    return RecyclerDom<O>().apply(builder)
}
