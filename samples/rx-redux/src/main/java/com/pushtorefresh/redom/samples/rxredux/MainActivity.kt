package com.pushtorefresh.redom.samples.rxredux

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pushtorefresh.redom.samples.rxredux.signin.SignInAndroidView
import com.pushtorefresh.redom.samples.rxredux.signin.SignInStateMachine
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = SignInAndroidView(findViewById(android.R.id.content))
        val stateMachine = SignInStateMachine(view.actions, RealSignInService(), Schedulers.computation())

        disposable += view.render(stateMachine.state)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}
