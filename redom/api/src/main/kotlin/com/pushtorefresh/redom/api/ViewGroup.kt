package com.pushtorefresh.redom.api

interface ViewGroup<O : Any> : View<O>, ViewParent<O> {

    override fun build(): ComponentGroup<O, out Any>
}

