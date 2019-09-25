package com.pushtorefresh.redom.samples.rxredux.signin

import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.StateAccessor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType

class SignInSuccessfulSideEffect(
    private val signInSuccessfulAction: () -> Unit // RxRedux Action naming sucks
) : SideEffect<SignInStateMachine.State, SignInStateMachine.Action> {

    override fun invoke(
        actions: Observable<SignInStateMachine.Action>,
        state: StateAccessor<SignInStateMachine.State>
    ): Observable<out SignInStateMachine.Action> =
        actions
            .ofType<SignInStateMachine.Action.SignInSuccessful>()
            .observeOn(AndroidSchedulers.mainThread())
            .switchMap {
                signInSuccessfulAction()
                Observable.empty<SignInStateMachine.Action>()
            }
}