package com.pushtorefresh.redom.samples.rxredux.signin

import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.reduxStore
import io.reactivex.Observable
import io.reactivex.Scheduler

class SignInStateMachine(
    inputActions: Observable<Action>,
    sideEffects: List<SideEffect<State, Action>>,
    initialState: State = State.Idle(email = "", password = "", signInButtonEnabled = false),
    reduxLoopScheduler: Scheduler
) {

    sealed class Action {
        data class ChangeEmail(val email: CharSequence) : Action()
        data class ChangePassword(val password: CharSequence) : Action()
        object SignIn : Action()
        object SigningIn : Action()
        object SignInSuccessful : Action()
        data class SignInFailed(val cause: Throwable) : Action()
    }

    sealed class State {

        abstract val email: CharSequence
        abstract val password: CharSequence
        abstract val signInButtonEnabled: Boolean

        // TODO extract state text

        data class Idle(
            override val email: CharSequence,
            override val password: CharSequence,
            override val signInButtonEnabled: Boolean
        ) : State()

        data class SigningIn(
            override val email: CharSequence,
            override val password: CharSequence,
            override val signInButtonEnabled: Boolean
        ) : State()

        data class SignInSuccessful(
            override val email: CharSequence,
            override val password: CharSequence,
            override val signInButtonEnabled: Boolean
        ) : State()

        data class SignInFailed(
            override val email: CharSequence,
            override val password: CharSequence,
            override val signInButtonEnabled: Boolean,
            val cause: Throwable
        ) : State()
    }


    val state: Observable<State> = inputActions
        .observeOn(reduxLoopScheduler)
        .distinctUntilChanged()
        .doOnNext { println("~~~ action = $it") }
        .reduxStore(
            initialState = initialState,
            sideEffects = sideEffects,
            reducer = ::reducer
        )
        .distinctUntilChanged()
        .doOnNext { println("~~~ state = $it") }

    private fun reducer(state: State, action: Action): State = when (action) {
        is Action.ChangeEmail -> {
            val email = action.email.trim()
            val password = state.password

            val signInButtonEnabled = validateEmailAndPassword(email, password)

            State.Idle(
                email = email,
                password = password,
                signInButtonEnabled = signInButtonEnabled
            )
        }
        is Action.ChangePassword -> {
            val email = state.email
            val password = action.password.trim()

            val signInButtonEnabled = validateEmailAndPassword(email, password)

            State.Idle(
                email = state.email,
                password = action.password,
                signInButtonEnabled = signInButtonEnabled
            )
        }
        is Action.SignIn, is Action.SigningIn -> State.SigningIn(
            email = state.email,
            password = state.password,
            signInButtonEnabled = false
        )
        is Action.SignInSuccessful -> State.SignInSuccessful(
            email = state.email,
            password = state.password,
            signInButtonEnabled = true
        )
        is Action.SignInFailed -> State.SignInFailed(
            email = state.email,
            password = state.password,
            cause = action.cause,
            signInButtonEnabled = true
        )
    }

    private fun validateEmailAndPassword(email: CharSequence, password: CharSequence): Boolean =
        email.isNotEmpty() && password.isNotEmpty()
}
