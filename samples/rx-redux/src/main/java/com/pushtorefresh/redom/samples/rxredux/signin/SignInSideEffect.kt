package com.pushtorefresh.redom.samples.rxredux.signin

import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.StateAccessor
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType

class SignInSideEffect(
    private val signInService: SignInService
) : SideEffect<SignInStateMachine.State, SignInStateMachine.Action> {

    override fun invoke(
        actions: Observable<SignInStateMachine.Action>,
        state: StateAccessor<SignInStateMachine.State>
    ): Observable<out SignInStateMachine.Action> =
        actions
            .ofType<SignInStateMachine.Action.SignIn>()
            .map { state() }
            .map { SignInService.Credentials(email = it.email, password = it.password) }
            .switchMap { credentials ->
                signInService
                    .signIn(credentials)
                    .map { result ->
                        when (result) {
                            is SignInService.SignInResult.Success -> SignInStateMachine.Action.SignInSuccessful
                            is SignInService.SignInResult.Error -> SignInStateMachine.Action.SignInFailed(result.cause)
                        }
                    }
            }
}