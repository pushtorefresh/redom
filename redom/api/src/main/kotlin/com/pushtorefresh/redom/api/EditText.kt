package com.pushtorefresh.redom.api

@Dom.RootDsl
interface EditText : TextView

@Suppress("UNCHECKED_CAST")
fun ViewParent.EditText(lambda: EditText.() -> Unit): Unit =
    lambda(createView(EditText::class.java))
