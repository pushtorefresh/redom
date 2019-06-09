package com.pushtorefresh.redom.android.view

import androidx.appcompat.widget.AppCompatButton
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxrelay2.PublishRelay
import com.pushtorefresh.redom.api.Button
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.toViewStructure
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

open class ButtonImpl<O : Any> : Button<O>, TextViewImpl<O>() {
    override fun build(): Component<O, out Any> {
        return ButtonComponent(
                changeEnabled,
                observeClicks,
                observeText,
                changeText,
                Button::class.java,
                Observable.merge(outputObservables)
        )
    }
}

private class ButtonComponent<O : Any>(
        private val changeEnabled: Observable<Boolean>?,
        private val observeClicks: PublishRelay<Any>?,
        private val observeText: PublishRelay<CharSequence>?,
        private val changeText: Observable<out CharSequence>?,
        override val clazz: Class<out View<*>>,
        override val output: Observable<O>
) : DefaultComponent<O, AppCompatButton>() {

    override fun bind(view: AppCompatButton): Disposable {
        val disposable = CompositeDisposable()
        if (changeText != null) disposable += changeText.subscribe(RxTextView.text(view))
        if (changeEnabled != null) disposable += changeEnabled.subscribe { view.isEnabled = it }
        if (observeClicks != null) disposable += RxView.clicks(view).subscribe(observeClicks)
        if (observeText != null) disposable += RxTextView.textChanges(view).subscribe(observeText)
        return disposable
    }
}
