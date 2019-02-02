package com.pushtorefresh.redom.android.view

import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxrelay2.PublishRelay
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.ComponentGroup
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.LinearLayout.Orientation.Horizontal
import com.pushtorefresh.redom.api.LinearLayout.Orientation.Vertical
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewGroup
import com.pushtorefresh.redom.api.ViewParent
import com.pushtorefresh.redom.api.toViewStructure
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

class LinearLayoutImpl<O : Any>(private val viewParent: ViewParent<O>) : LinearLayout<O> {

    private val outputObservables = mutableListOf<Observable<O>>()
    private val views = mutableListOf<View<O, *, *>>()
    private var observeClicks: PublishRelay<Any>? = null
    private var _orientation: Observable<LinearLayout.Orientation>? = null

    override val observe = object : LinearLayout.Observe {
        override val clicks: Observable<Any>
            get() = observeClicks ?: PublishRelay.create<Any>().also {
                observeClicks = it
            }
    }

    override val change = object : LinearLayout.Change {
        override var orientation: Observable<LinearLayout.Orientation>
            get() = throw IllegalAccessError()
            set(value) {
                _orientation = value
            }
    }

    override val output = object : View.Output<O> {
        override fun plusAssign(observable: Observable<O>) {
            outputObservables += observable
        }
    }

    override fun <Ob : View.Observe, Ch : View.Change, V : View<O, Ob, Ch>> createView(clazz: Class<out V>): V {
        return viewParent.createView(clazz).also { views += it }
    }

    override fun build(): ComponentGroup<O, out Any> {
        val children = views.map { it.build() }

        return LinearLayoutComponent(observeClicks,
                                     _orientation,
                                     children,
                                     Observable.merge(outputObservables)
        )
    }
}

private class LinearLayoutComponent<O : Any>(private val observeClicks: PublishRelay<Any>?,
                                             private val orientation: Observable<LinearLayout.Orientation>?,
                                             override val children: List<Component<O, out Any>>,
                                             override val output: Observable<O>) :
        ComponentGroup<O, android.widget.LinearLayout> {

    override val clazz: Class<out ViewGroup<out Any, out ViewGroup.Observe, out ViewGroup.Change>> = LinearLayout::class.java
    override val viewStructure = toViewStructure(this)

    override fun bind(view: android.widget.LinearLayout): Disposable {
        val disposable = CompositeDisposable()
        if (observeClicks != null) disposable += RxView.clicks(view)
                .subscribe(observeClicks)
        if (orientation != null) disposable += orientation.subscribe {
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            view.orientation = when (it) {
                Horizontal -> android.widget.LinearLayout.HORIZONTAL
                Vertical -> android.widget.LinearLayout.VERTICAL
            }
        }

        children.forEachIndexed { index, component ->
            @Suppress("UNCHECKED_CAST")
            disposable += (component as Component<O, android.view.View>).bind(view.getChildAt(index))
        }

        return disposable
    }
}
