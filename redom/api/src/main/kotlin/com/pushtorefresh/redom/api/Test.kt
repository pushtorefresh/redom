package com.pushtorefresh.redom.api

import com.pushtorefresh.redom.api.LinearLayout.Orientation.Vertical
import io.reactivex.Observable

private fun <O> AndroidDom(lambda: Dom<O>.() -> Unit): Dom<O> = TODO()

private sealed class MyUiAction {
    object MyTextViewClick : MyUiAction()
}

private fun f() {
    AndroidDom<MyUiAction> {

        TextView {

            change.text = Observable.just("asd")

            change {
                text = Observable.just("yoba")
            }

            output += observe.textChanges.map { MyUiAction.MyTextViewClick }

            observe {
                output += clicks.map { MyUiAction.MyTextViewClick }
                output += clicks.map { MyUiAction.MyTextViewClick }
                output += clicks.map { MyUiAction.MyTextViewClick }
                output += textChanges.map { MyUiAction.MyTextViewClick }
            }
        }

        LinearLayout {

            init {
                orientation = Vertical
            }

            TextView {
                // this call should go to LinearLayout
            }
        }
    }.build()
}

fun <O> render(components: List<Component>) : RenderResult<O> {
    TODO()
}

data class RenderResult<O>(val output: Observable<O>,
                           val rawOutput: Observable<Any>)