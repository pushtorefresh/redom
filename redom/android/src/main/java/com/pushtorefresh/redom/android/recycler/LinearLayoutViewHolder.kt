package com.pushtorefresh.redom.android.recycler

import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.view.RxViewGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxrelay2.Relay
import com.pushtorefresh.redom.android.view.LinearLayoutImpl
import com.pushtorefresh.redom.android.view.TextViewImpl
import com.pushtorefresh.redom.api.ComponentGroup
import com.pushtorefresh.redom.api.LinearLayout.*
import com.pushtorefresh.redom.api.TextView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign

class LinearLayoutViewHolder(private val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout) {

    private val disposable = CompositeDisposable()

    fun bind(componentGroup: ComponentGroup<*>) {
        disposable.clear()
        linearLayout.removeAllViews() // TODO remove and generate proper ViewHolders depending on ComponentGroup structure.

        componentGroup
                .observeProperties
                .entries
                .forEach { property ->
                    val name = property.key
                    @Suppress("UNCHECKED_CAST") val relay = property.value as Relay<Any>

                    val observable = when (name) {
                        LinearLayoutImpl.O_PROPERTY_CLICKS -> RxView.clicks(linearLayout)
                        else -> throw IllegalArgumentException("Unsupported observeProperty $name")
                    }

                    disposable += observable.subscribe(relay)
                }

        disposable += componentGroup.output.subscribe()

        componentGroup
                .changeProperties
                .entries
                .forEach { property ->
                    val name = property.key
                    val observable = property.value

                    @Suppress("UNCHECKED_CAST")
                    val consumer = when (name) {
                        LinearLayoutImpl.C_PROPERTY_ORIENTATION -> Consumer<Orientation> {
                            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
                            when (it) {
                                Orientation.Horizontal -> linearLayout.orientation = LinearLayout.HORIZONTAL
                                Orientation.Vertical -> linearLayout.orientation = LinearLayout.VERTICAL
                            }
                        }
                        else -> throw IllegalArgumentException("Unsupported changeProperty $name")
                    } as Consumer<Any>

                    disposable += observable.subscribe(consumer)
                }
    }
}
