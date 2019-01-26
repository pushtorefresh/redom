package com.pushtorefresh.redom.api

interface ViewGroup<O : Any, Ob: ViewGroup.Observe, Ch: ViewGroup.Change> : View<O, Ob, Ch>, ViewParent<O> {

    interface Observe : View.Observe {

    }

    interface Change : View.Change {

    }

    override fun build(): ComponentGroup<O, out Any>
}

