package com.pushtorefresh.redom.samples.rxredux

import com.pushtorefresh.redom.samples.rxredux.signin.SignInService
import com.pushtorefresh.redom.samples.rxredux.signin.SignInService.SignInResult
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit.SECONDS

class RealSignInService : SignInService {
    override fun signIn(credentials: SignInService.Credentials) = Observable
        .fromCallable { true }
        .delay { Observable.timer(Math.max(2, Random().nextInt(5)).toLong(), SECONDS) }
        .map {
            when (it) {
                true -> SignInResult.Success
                false -> SignInResult.Error(cause = Exception("some network problem"))
            }
        }
}
