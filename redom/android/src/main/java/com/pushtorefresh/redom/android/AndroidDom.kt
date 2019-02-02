package com.pushtorefresh.redom.android

import com.pushtorefresh.redom.android.view.AndroidViewParent
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.Dom
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewParent

internal class AndroidDom<O : Any>(private val viewParent: ViewParent<O>) : Dom<O> {

    private val views = mutableListOf<View<O, *, *>>()

    override fun <Ob : View.Observe, Ch : View.Change, V : View<O, Ob, Ch>> createView(clazz: Class<out V>): V =
            viewParent.createView(clazz).also { views += it }

    override fun build(): List<Component<O, *>> =
            views.map(View<O, *, *>::build)

}

fun <O : Any> androidDom(viewParent: ViewParent<O> = AndroidViewParent(), builder: Dom<O>.() -> Unit): Dom<O> {
    return AndroidDom(viewParent).apply(builder)
}
