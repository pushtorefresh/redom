package com.pushtorefresh.redom.android.view

import androidx.appcompat.widget.AppCompatTextView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxrelay2.PublishRelay
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.toViewStructure
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

class TextViewImpl<O : Any> : TextView<O> {

    private val outputObservables = mutableListOf<Observable<O>>()
    private var observeClicks: PublishRelay<Any>? = null
    private var observeText: PublishRelay<CharSequence>? = null
    private var changeText: Observable<out CharSequence>? = null

    override val observe = object : TextView.Observe {
        override val textChanges: Observable<CharSequence>
            get() = observeText ?: PublishRelay.create<CharSequence>().also {
                observeText = it
            }

        override val clicks: Observable<Any>
            get() = observeClicks ?: PublishRelay.create<Any>().also {
                observeClicks = it
            }
    }

    override val change = object : TextView.Change {
        override var text: Observable<out CharSequence>
            get() = throw IllegalAccessError()
            set(value) {
                changeText = value
            }
    }

    override val output = object : View.Output<O> {
        override fun plusAssign(observable: Observable<O>) {
            outputObservables += observable
        }
    }

    override fun build(): Component<O, *> {
        return TextViewComponent(observeClicks,
                                 observeText,
                                 changeText,
                                 TextView::class.java,
                                 Observable.merge(outputObservables)
        )
    }
}

private class TextViewComponent<O : Any>(private val observeClicks: PublishRelay<Any>?,
                                   private val observeText: PublishRelay<CharSequence>?,
                                   private val changeText: Observable<out CharSequence>?,
                                   override val clazz: Class<out View<*, *, *>>,
                                   override val output: Observable<O>) : Component<O, AppCompatTextView> {
    override val viewStructure = toViewStructure(this)
    override fun bind(view: AppCompatTextView): Disposable {
        val disposable = CompositeDisposable()
        if(observeClicks != null) disposable += RxView.clicks(view).subscribe(observeClicks)
        if(observeText != null) disposable += RxTextView.textChanges(view).subscribe(observeText)
        if(changeText != null) disposable += changeText.subscribe(RxTextView.text(view))
        return disposable
    }
}
