package com.pushtorefresh.redom.api

import com.pushtorefresh.redom.api.LinearLayout.Orientation.Vertical
import io.reactivex.Observable

private fun <O> RecyclerDom(lambda: Dom<O>.() -> Unit): Dom<O> = TODO()

private sealed class MyUiAction {
    object MyTextViewClick : MyUiAction()
}

private fun f() {
    val dom = RecyclerDom<MyUiAction> {

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

            change {
                orientation = Observable.just(Vertical)
            }

            TextView {
                // this call should go to LinearLayout
            }
        }
    }.build()

    render(dom)
}

fun <O> render(dom: ComponentGroup<O>) : RenderResult<O> {
    TODO()
}

data class RenderResult<O>(val output: Observable<O>,
                           val rawOutput: Observable<Any>)
