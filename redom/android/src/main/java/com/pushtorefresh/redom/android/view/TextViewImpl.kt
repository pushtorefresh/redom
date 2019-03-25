package com.pushtorefresh.redom.android.view

import androidx.appcompat.widget.AppCompatTextView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxrelay2.PublishRelay
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewImpl
import com.pushtorefresh.redom.api.toViewStructure
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

open class TextViewImpl<O : Any> : TextView<O>, ViewImpl<O, TextView.Observe, TextView.Change>() {

    protected var observeText: PublishRelay<CharSequence>? = null
        private set(value) {
            field = value
        }

    protected var changeText: Observable<out CharSequence>? = null

    override val observe: TextView.Observe = TextViewObserveImpl()

    override val change = object : TextView.Change {
        override var text: Observable<out CharSequence>
            get() = throw IllegalAccessError()
            set(value) {
                changeText = value
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

    private inner class TextViewObserveImpl : ViewObserveImpl(), TextView.Observe {
        override val textChanges: Observable<out CharSequence>
            get() = observeText ?: PublishRelay.create<CharSequence>().also {
                observeText = it
            }
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
