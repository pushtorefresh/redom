package com.pushtorefresh.redom.samples.rxredux.signin

import com.pushtorefresh.redom.samples.rxredux.signin.SignInStateMachine.Action
import com.pushtorefresh.redom.samples.rxredux.signin.SignInStateMachine.State
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface SignInView {
    val actions: Observable<Action>
    fun render(stateStream: Observable<State>): Disposable
}
