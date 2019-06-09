package com.pushtorefresh.redom.android.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pushtorefresh.redom.api.Component
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class ComponentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val disposable = CompositeDisposable()

    fun bind(component: Component<out Any, Any>) {
        disposable.clear()
        disposable += component.bind(itemView)
    }

    fun unbind(): Unit = disposable.clear()
}
