package com.pushtorefresh.redom.api

interface Button<O : Any> : TextView<O> {

}

fun <O : Any> ViewParent<O>.Button(lambda: Button<O>.() -> Unit): Unit = lambda(createView(Button::class.java as Class<Button<O>>))