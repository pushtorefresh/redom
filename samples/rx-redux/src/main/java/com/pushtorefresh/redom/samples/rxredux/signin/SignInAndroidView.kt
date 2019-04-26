package com.pushtorefresh.redom.samples.rxredux.signin

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import com.pushtorefresh.redom.android.androidDom
import com.pushtorefresh.redom.android.recycler.Adapter
import com.pushtorefresh.redom.android.recycler.Inflater
import com.pushtorefresh.redom.android.recycler.ViewTypeRegistryImpl
import com.pushtorefresh.redom.api.Button
import com.pushtorefresh.redom.api.EditText
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.samples.rxredux.signin.SignInStateMachine.Action
import com.pushtorefresh.redom.samples.rxredux.signin.SignInStateMachine.State
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.Disposable

class SignInAndroidView(root: ViewGroup) : SignInView {

    private val adapter: Adapter

    init {
        val recyclerView = RecyclerView(root.context)
        adapter = Adapter(ViewTypeRegistryImpl(), Inflater)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        root.findViewById<ViewGroup>(android.R.id.content).addView(recyclerView)
    }

    override val actions: Observable<Action> = PublishRelay.create()

    override fun render(stateStream: Observable<State>): Disposable = stateStream
        .observeOn(mainThread())
        .subscribe { state ->
            adapter.setComponents(androidDom<Unit> {
                LinearLayout {
                    EditText {
                        text.subscribe {
                            //actions.accept(it)
                        }
                    }

                    EditText {
                       // actions.accept(it)
                    }

                    Button {
                        text = Observable.just("Sign In")
                    }

                    TextView {
                        text = when (state) {
                            is State.Idle -> ""
                            is State.SigningIn -> "Signing in…"
                            is State.SignInSuccessful -> "Successfully signed in!"
                            is State.SignInFailed -> "Couldn't sign in because ${state.cause.message}"
                        }
                            .let { Observable.just(it) }
                    }
                }
            }.build())
        }
}
