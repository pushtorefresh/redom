package com.pushtorefresh.redom.android.view

import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxrelay2.PublishRelay
import com.pushtorefresh.redom.api.CheckBox
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.CompoundButton
import com.pushtorefresh.redom.api.Switch
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.toViewStructure
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

abstract class CompundButtonImpl<O : Any> : ButtonImpl<O>(), CompoundButton<O> {
    protected var toggle: Observable<Any>? = null
    protected var changeChecked: Observable<Boolean>? = null
    protected var observeChecked: PublishRelay<Boolean>? = null

    override var checked: Observable<Boolean>
        get() = observeChecked ?: PublishRelay.create<Boolean>().apply {
            observeChecked = this
        }
        set(value) {
            changeChecked = value
        }

    override fun toggle(toggle: Observable<Any>) {
        this.toggle = toggle
    }

    override fun build(): Component<O, out Any> {
        return CompoundButtonComponent(
            observeClicks,
            observeText,
            observeChecked,
            changeText,
            changeChecked,
            toggle,
            getCompoundClass(),
            Observable.merge(outputObservables)
        )
    }

    abstract fun getCompoundClass(): Class<out View<*>>
}

class SwitchImpl<O : Any> : CompundButtonImpl<O>(), Switch<O> {

    override fun getCompoundClass(): Class<out View<*>> {
        return Switch::class.java
    }

}

class CheckBoxImpl<O : Any> : CompundButtonImpl<O>(), CheckBox<O> {

    override fun getCompoundClass(): Class<out View<*>> {
        return CheckBox::class.java
    }

}

private class CompoundButtonComponent<O : Any>(
    private val observeClicks: PublishRelay<Any>?,
    private val observeText: PublishRelay<CharSequence>?,
    private val observeChecked: PublishRelay<Boolean>?,
    private val changeText: Observable<out CharSequence>?,
    private val changeChecked: Observable<out Boolean>?,
    private val toggle: Observable<Any>?,
    override val clazz: Class<out View<*>>,
    override val output: Observable<O>
) : Component<O, android.widget.CompoundButton> {

    override val viewStructure = toViewStructure(this)

    override fun bind(view: android.widget.CompoundButton): Disposable {
        val disposable = CompositeDisposable()
        if (observeClicks != null) disposable += RxView.clicks(view).subscribe(observeClicks)
        if (observeText != null) disposable += RxTextView.textChanges(view).subscribe(observeText)
        if (observeChecked != null) disposable += RxCompoundButton.checkedChanges(view).subscribe(observeChecked)
        if (changeText != null) disposable += changeText.subscribe(RxTextView.text(view))
        if (changeChecked != null) disposable += changeChecked.subscribe(RxCompoundButton.checked(view))
        if (toggle != null) disposable += toggle.subscribe(RxCompoundButton.toggle(view))
        return disposable
    }
}
