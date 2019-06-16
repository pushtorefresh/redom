package com.pushtorefresh.redom.api

interface CompoundButton : Button {

    var checked: Boolean
    var onCheckedChange: ((Boolean) -> Unit)?
}

interface Switch : CompoundButton

fun ViewParent.Switch(lambda: Switch.() -> Unit): Unit =
    lambda(createView(Switch::class.java))

interface CheckBox : CompoundButton

fun ViewParent.CheckBox(lambda: CheckBox.() -> Unit): Unit =
    lambda(createView(CheckBox::class.java))
