package com.pushtorefresh.redom.api

interface Button : TextView

fun ViewParent.Button(lambda: Button.() -> Unit): Unit =
    lambda(createView(Button::class.java))
