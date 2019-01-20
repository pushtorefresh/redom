package com.pushtorefresh.redom.android.view

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.View
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class TextViewImpl<O> : TextView<O> {

    private val observeProperties = mutableMapOf<String, Relay<*>>()
    private val changeProperties = mutableMapOf<String, Observable<*>>()
    private val outputObservables = mutableListOf<Observable<O>>()
    private val rawOutput = PublishSubject.create<Any>()

    override val observe = object : TextView.Observe {
        override val textChanges
            get() = observeProperties.getOrPutAndGet(O_PROPERTY_TEXT_CHANGES) { PublishRelay.create<CharSequence>() }

        override val clicks
            get() = observeProperties.getOrPutAndGet(O_PROPERTY_CLICKS) { PublishRelay.create<Any>() }
    }

    override val change = object : TextView.Change {
        @Suppress("UNCHECKED_CAST")
        override var text: Observable<out CharSequence>
            get() = throw IllegalAccessError()
            set(value) {
                changeProperties[C_PROPERTY_TEXT] = value
            }
    }

    override val output = object : View.Output<O> {
        override fun plusAssign(observable: Observable<O>) {
            outputObservables += observable
        }
    }

    override fun build(): Component<O> {
        return Component.create(
                TextView::class.java,
                Observable.merge(outputObservables),
                rawOutput,
                observeProperties,
                changeProperties
        )
    }

    companion object {
        const val C_PROPERTY_TEXT = "c-text"
        const val O_PROPERTY_CLICKS = "o-clicks"
        const val O_PROPERTY_TEXT_CHANGES = "o-textChanges"
    }

}
