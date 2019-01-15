package com.pushtorefresh.redom.android.recycler

import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.pushtorefresh.redom.android.view.TextViewImpl
import com.pushtorefresh.redom.api.Component
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class TextViewViewHolder(private val textView: TextView) : RecyclerView.ViewHolder(textView) {
    private val disposable = CompositeDisposable()

    fun bind(component: Component<*>) {
        disposable.clear()
        disposable += component
            .getChangeProperty<CharSequence>(TextViewImpl.C_PROPERTY_TEXT)
            .subscribe { text ->
                textView.text = text
            }
    }
}
