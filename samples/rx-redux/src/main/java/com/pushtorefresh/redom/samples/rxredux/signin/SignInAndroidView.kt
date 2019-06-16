package com.pushtorefresh.redom.samples.rxredux.signin

import android.util.Log
import android.view.View
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
import com.pushtorefresh.redom.api.LinearLayout.Orientation
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.samples.rxredux.signin.SignInStateMachine.Action
import com.pushtorefresh.redom.samples.rxredux.signin.SignInStateMachine.State
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class SignInAndroidView(root: ViewGroup) : SignInView {

    private val adapter: Adapter

    init {
        val recyclerView = RecyclerView(root.context)
        adapter = Adapter(ViewTypeRegistryImpl(), Inflater)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                Log.i("RecyclerView", "detach ${view::class.java}")
            }

            override fun onChildViewAttachedToWindow(view: View) {
                Log.i("RecyclerView", "attach ${view::class.java}")
            }
        })
        root.findViewById<ViewGroup>(android.R.id.content).addView(recyclerView)
    }

    override val actions = PublishRelay.create<Action>()

    override fun render(stateStream: Observable<State>): Disposable = stateStream
        .observeOn(Schedulers.computation())
        .map { state ->
            androidDom<Unit> {
                LinearLayout {

                    orientation = Orientation.Vertical

                    EditText {
                        text = state.email

                        onTextChange = { actions.accept(Action.ChangeEmail(it)) }
                    }

                    EditText {
                        text = state.password

                        onTextChange = { actions.accept(Action.ChangePassword(it)) }
                    }

                    Button {
                        text = "Sign In"
                        enabled = state.signInButtonEnabled

                        onClick = { actions.accept(Action.SignIn) }
                    }

                    TextView {
                        text = when (state) {
                            is State.Idle -> ""
                            is State.SigningIn -> "Signing in…"
                            is State.SignInSuccessful -> "Successfully signed in!"
                            is State.SignInFailed -> "Couldn't sign in because ${state.cause.message}"
                        }
                    }
                }
            }.build()
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { adapter.setComponents(it) }

}
