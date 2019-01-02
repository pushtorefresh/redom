// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.android

import com.jakewharton.rxbinding2.view.RxView
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.rendering.Renderer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import java.util.concurrent.atomic.AtomicReferenceArray

class AndroidView(
        private val realView: android.view.View,
        private val renderer: Renderer
) : View {

    companion object {
        private const val STATE_INDEX_ACTIVATED = 0
        private const val STATE_INDEX_ALPHA = 1
        private const val STATE_INDEX_ENABLED = 2
        private const val STATE_INDEX_FOCUSABLE = 3
        private const val STATE_INDEX_FOCUSABLE_IN_TOUCH_MODE = 4
        private const val STATE_INDEX_VISIBLE = 5
    }

    private val state = AtomicReferenceArray<Any>(6)

    override val observe = object : View.Observe {

        override val clicks: Observable<Any> by lazy {
            RxView
                    .clicks(realView)
                    .subscribeOn(mainThread())
                    .share()
        }
    }

    override val change = object : View.Change {

    }
}
