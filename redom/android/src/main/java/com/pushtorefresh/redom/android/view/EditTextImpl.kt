package com.pushtorefresh.redom.android.view

import androidx.appcompat.widget.AppCompatEditText
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxrelay2.PublishRelay
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.EditText
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.toViewStructure
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

class EditTextImpl<O : Any> : EditText<O>, TextViewImpl<O>() {

    override fun build(): Component<O, *> {
        return EditTextComponent(
            observeClicks,
            observeText,
            changeText,
            EditText::class.java,
            Observable.merge(outputObservables)
        )
    }

    private class EditTextComponent<O : Any>(
        private val observeClicks: PublishRelay<Any>?,
        private val observeText: PublishRelay<CharSequence>?,
        private val changeText: Observable<out CharSequence>?,
        override val clazz: Class<out View<*>>,
        override val output: Observable<O>
    ) : Component<O, AppCompatEditText> {

        override val viewStructure = toViewStructure(this)

        override fun bind(view: AppCompatEditText): Disposable {
            val disposable = CompositeDisposable()

            observeClicks?.also { disposable += RxView.clicks(view).subscribe(it) }
            observeText?.also { disposable += RxTextView.textChanges(view).subscribe(it) }
            changeText?.also { disposable += it.subscribe(RxTextView.text(view)) }

            // TODO bind output.

            return disposable
        }
    }

}
