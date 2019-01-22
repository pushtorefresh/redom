package com.pushtorefresh.redom.android

import com.pushtorefresh.redom.android.view.AndroidViewParent
import com.pushtorefresh.redom.api.*
import io.reactivex.Observable

internal class RecyclerDom<O>(private val viewParent: ViewParent<O>) : Dom<O> {

    private val views = mutableListOf<View<O, *, *>>()

    override fun <Ob : View.Observe, Ch : View.Change, V : View<O, Ob, Ch>> createView(clazz: Class<out V>): V =
            viewParent.createView(clazz).also { views += it }

    override fun build(): ComponentGroup<O> {
        val childComponents = views.map(View<O, *, *>::build)
        return ComponentGroup.create(
            LinearLayout::class.java,
            Observable.empty(),
            Observable.empty<Any>(),
            emptyMap(),
            emptyMap(),
            childComponents
        )
    }

}

fun <O> androidDom(viewParent: ViewParent<O> = AndroidViewParent(), builder: Dom<O>.() -> Unit): Dom<O> {
    return RecyclerDom(viewParent).apply(builder)
}
