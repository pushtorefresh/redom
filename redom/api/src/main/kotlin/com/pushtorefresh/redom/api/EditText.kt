package com.pushtorefresh.redom.api

@Dom.RootDsl
interface EditText<O : Any> : TextView<O> {

}

@Suppress("UNCHECKED_CAST")
fun <O : Any> ViewParent<O>.EditText(lambda: EditText<O>.() -> Unit): Unit =
    lambda(createView(EditText::class.java as Class<EditText<O>>))
