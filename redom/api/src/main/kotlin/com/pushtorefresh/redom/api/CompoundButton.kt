package com.pushtorefresh.redom.api

import io.reactivex.Observable

interface CompoundButton<O : Any> : Button<O> {

    var checked: Observable<Boolean>

    fun toggle(toggle: Observable<Any>)
}

interface Switch<O : Any> : CompoundButton<O>

fun <O : Any> ViewParent<O>.Switch(lambda: Switch<O>.() -> Unit): Unit = lambda(createView(Switch::class.java as Class<Switch<O>>))

interface CheckBox<O : Any> : CompoundButton<O>

fun <O : Any> ViewParent<O>.CheckBox(lambda: CheckBox<O>.() -> Unit): Unit = lambda(createView(CheckBox::class.java as Class<CheckBox<O>>))
