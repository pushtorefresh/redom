// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

inline fun <T> Observable<T>.subscribe(crossinline bindFunc: (stream: Observable<T>) -> Disposable): Disposable =
        bindFunc.invoke(this)

