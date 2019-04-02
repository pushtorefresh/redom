package com.pushtorefresh.redom.android.view

import androidx.appcompat.widget.AppCompatEditText
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.EditText
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.toViewStructure
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class EditTextImpl<O : Any> : EditText<O>, TextViewImpl<O>() {

    override fun build(): Component<O, *> {
        return EditTextComponent(
                this,
                EditText::class.java,
                Observable.merge(outputObservables)
        )
    }

    private class EditTextComponent<O : Any>(
            private val editTextImpl: EditTextImpl<O>,
            override val clazz: Class<out View<*>>,
            override val output: Observable<O>
    ) : Component<O, AppCompatEditText> {

        override val viewStructure = toViewStructure(this)

        override fun bind(view: AppCompatEditText): Disposable {
            val disposable = CompositeDisposable()

            editTextImpl.observeClicks?.also { RxView.clicks(view).subscribe(it) }
            editTextImpl.observeText?.also { RxTextView.textChanges(view).subscribe(it) }
            editTextImpl.changeText?.also { it.subscribe(RxTextView.text(view)) }

            return disposable
        }
    }

}
