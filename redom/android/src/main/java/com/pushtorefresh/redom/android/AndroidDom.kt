package com.pushtorefresh.redom.android

import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.Dom
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.View
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class AndroidDom<O> : Dom<O> {
    override fun build() = TODO()

    override fun <Ob : View.Observe, Ch : View.Change, V : View<O, Ob, Ch>> createDsl(clazz: Class<out V>): V {
        return when (clazz) {
            TextView::class.java -> object : TextView<O> {

                private val O_PROPERTY_TEXT_CHANGES = "o-textChanges"
                private val O_PROPERTY_CLICKS= "o-clicks"
                private val C_PROPERTY_TEXT= "c-text"

                private val outputObservables = mutableListOf<Observable<O>>()
                private val rawOutput = PublishSubject.create<Any>()
                private val initProperties = mutableMapOf<String, Any>()
                private val observeProperties = mutableMapOf<String, Observable<*>>()
                private val changeProperties = mutableMapOf<String, Observable<*>>()

                override val observe = object: TextView.Observe {
                    override val textChanges = observeProperties[O_PROPERTY_TEXT_CHANGES] as Observable<out CharSequence>
                    override val clicks = observeProperties[O_PROPERTY_CLICKS] as Observable<Any>
                }

                override val change = object: TextView.Change {
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

            } as V

            else -> TODO()
        }
    }
}
