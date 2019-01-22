package com.pushtorefresh.redom.android.view

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.pushtorefresh.redom.api.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class LinearLayoutImpl<O>(private val viewParent: ViewParent<O>) : LinearLayout<O> {

    private val observeProperties = mutableMapOf<String, Relay<*>>()
    private val changeProperties = mutableMapOf<String, Observable<*>>()
    private val outputObservables = mutableListOf<Observable<O>>()
    private val rawOutput = PublishSubject.create<Any>()
    private val views = mutableListOf<View<O, *, *>>()

    override val observe = object : LinearLayout.Observe {
        override val clicks
            get() = observeProperties.getOrPutAndGet(O_PROPERTY_CLICKS) { PublishRelay.create<Any>()}
    }

    override val change = object : LinearLayout.Change {
        override var orientation: Observable<LinearLayout.Orientation>
            get() = throw IllegalAccessError()
            set(value) {
                changeProperties[C_PROPERTY_ORIENTATION] = value
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

    override fun build(): ComponentGroup<O> {
        val children = views.map { it.build() }

        return ComponentGroup.create(
                LinearLayout::class.java,
                Observable.merge(outputObservables),
                rawOutput,
                observeProperties,
                changeProperties,
                children
        )
    }

    companion object {
        const val O_PROPERTY_CLICKS = "o-clicks"
        const val C_PROPERTY_ORIENTATION = "c-orientation"
    }
}
