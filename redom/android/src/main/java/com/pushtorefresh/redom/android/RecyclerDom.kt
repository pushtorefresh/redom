package com.pushtorefresh.redom.android

import com.pushtorefresh.redom.android.view.TextViewImpl
import com.pushtorefresh.redom.api.*
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
