package com.pushtorefresh.redom.android.view

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.View
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class TextViewImpl<O> : TextView<O> {

    private val outputObservables = mutableListOf<Observable<O>>()
    private val rawOutput = PublishSubject.create<Any>()
    private val initProperties = mutableMapOf<String, Any>()
    private val observeProperties = mutableMapOf<String, Relay<*>>()
    private val changeProperties = mutableMapOf<String, Observable<*>>()

    override val observe = object : TextView.Observe {
        override val textChanges
            get() = setupObserveProperty<CharSequence>(O_PROPERTY_TEXT_CHANGES)

        override val clicks
            get() = setupObserveProperty<Any>(O_PROPERTY_CLICKS)

        @Suppress("UNCHECKED_CAST")
        fun <T> setupObserveProperty(name: String): Observable<T> {
            var observable = observeProperties[name]

            if (observable == null) {
                observable = PublishRelay.create<T>()
                observeProperties[name] = observable
            }

            return observable as Observable<T>
        }
    }

    override val change = object : TextView.Change {
        override var text: Observable<out CharSequence>
            get() = changeProperties[C_PROPERTY_TEXT] as Observable<out CharSequence>
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
                initProperties,
                observeProperties,
                changeProperties
        )
    }

    companion object {
        val C_PROPERTY_TEXT = "c-text"
        val O_PROPERTY_CLICKS = "o-clicks"
        val O_PROPERTY_TEXT_CHANGES = "o-textChanges"
    }

}
