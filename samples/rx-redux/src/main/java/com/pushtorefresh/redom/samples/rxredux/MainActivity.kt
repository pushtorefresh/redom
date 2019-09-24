package com.pushtorefresh.redom.samples.rxredux

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pushtorefresh.redom.samples.rxredux.feed.FeedActivity
import com.pushtorefresh.redom.samples.rxredux.signin.SignInAndroidView
import com.pushtorefresh.redom.samples.rxredux.signin.SignInSideEffect
import com.pushtorefresh.redom.samples.rxredux.signin.SignInStateMachine
import com.pushtorefresh.redom.samples.rxredux.signin.SignInSuccessfulSideEffect
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = SignInAndroidView(findViewById(android.R.id.content))
        val stateMachine = SignInStateMachine(
            inputActions = view.actions,
            sideEffects = listOf(
                SignInSideEffect(
                    signInService = RealSignInService()
                ),
                SignInSuccessfulSideEffect {
                    startActivity(Intent(this, FeedActivity::class.java))
                }
            ),
            reduxLoopScheduler = Schedulers.computation()
        )

        disposable += view.render(stateMachine.state)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}
