package com.pushtorefresh.redom.api

interface ViewGroup<O : Any, Ob: View.Observe, Ch: View.Change> : View<O, Ob, Ch>, ViewParent<O> {

    override fun build(): ComponentGroup<O, out Any>
}

