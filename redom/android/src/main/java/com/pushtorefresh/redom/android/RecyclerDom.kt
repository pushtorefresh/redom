package com.pushtorefresh.redom.android

import com.pushtorefresh.redom.android.view.AndroidViewParent
import com.pushtorefresh.redom.api.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

internal class RecyclerDom<O : Any>(private val viewParent: ViewParent<O>) : Dom<O> {

    private val views = mutableListOf<View<O, *, *>>()

    override fun <Ob : View.Observe, Ch : View.Change, V : View<O, Ob, Ch>> createView(clazz: Class<out V>): V =
            viewParent.createView(clazz).also { views += it }

    override fun build(): ComponentGroup<O, *> {
        val childComponents = views.map(View<O, *, *>::build)
        return object : ComponentGroup<O, Any> {
            override val children: List<Component<O, out Any>> = childComponents
            override val clazz: Class<out ViewGroup<*, *, *>>
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            override val output: Observable<O>
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            override val rawOutput: Observable<*>
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

            override fun bind(view: Any): Disposable {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

}

fun <O : Any> androidDom(viewParent: ViewParent<O> = AndroidViewParent(), builder: Dom<O>.() -> Unit): Dom<O> {
    return RecyclerDom(viewParent).apply(builder)
}
