package com.pushtorefresh.redom.android.recycler

import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxrelay2.Relay
import com.pushtorefresh.redom.android.view.TextViewImpl
import com.pushtorefresh.redom.api.Component
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign

class TextViewViewHolder(private val textView: AppCompatTextView) : RecyclerView.ViewHolder(textView) {
    private val disposable = CompositeDisposable()

    fun bind(component: Component<*>) {
        disposable.clear()

        component
                .observeProperties
                .entries
                .forEach { property ->
                    val name = property.key
                    @Suppress("UNCHECKED_CAST") val relay = property.value as Relay<Any>

                    val observable = when (name) {
                        TextViewImpl.O_PROPERTY_TEXT_CHANGES -> RxTextView.textChanges(textView)
                        TextViewImpl.O_PROPERTY_CLICKS -> RxView.clicks(textView)
                        else -> throw IllegalArgumentException("Unsupported observeProperty $name")
                    }

                    disposable += observable.subscribe(relay)
                }

        disposable += component.output.subscribe()

        component
                .changeProperties
                .entries
                .forEach { property ->
                    val name = property.key
                    val observable = property.value

                    @Suppress("UNCHECKED_CAST")
                    val consumer = when (name) {
                        TextViewImpl.C_PROPERTY_TEXT -> RxTextView.text(textView)
                        else -> throw IllegalArgumentException("Unsupported changeProperty $name")
                    } as Consumer<Any>

                    disposable += observable.subscribe(consumer)
                }
    }
}
