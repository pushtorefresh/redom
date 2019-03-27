package com.pushtorefresh.redom.api

import com.pushtorefresh.redom.api.LinearLayout.Orientation.Vertical
import io.reactivex.Observable

private fun <O : Any> RecyclerDom(lambda: Dom<O>.() -> Unit): Dom<O> = TODO()

private sealed class MyUiAction {
    object MyTextViewClick : MyUiAction()
}

private fun f() {
    val dom = RecyclerDom<MyUiAction> {

        TextView {

            text = Observable.just("asd")

            text = Observable.just("yoba")

            output += text.map { MyUiAction.MyTextViewClick }

            output += clicks.map { MyUiAction.MyTextViewClick }
            output += clicks.map { MyUiAction.MyTextViewClick }
            output += clicks.map { MyUiAction.MyTextViewClick }
            output += text.map { MyUiAction.MyTextViewClick }
        }

        LinearLayout {

            orientation = Observable.just(Vertical)

            TextView {
                // this call should go to LinearLayout
            }
        }
    }.build()

    render(dom)
}

private fun <O : Any> render(dom: List<Component<O, *>>): RenderResult<O> {
    TODO()
}

data class RenderResult<O>(val output: Observable<O>,
                           val rawOutput: Observable<Any>)
