package com.pushtorefresh.redom.api

interface ViewGroup<O, Ob: ViewGroup.Observe, Ch: ViewGroup.Change> : Dom<O>, View<O, Ob, Ch> {

    interface Observe : View.Observe {

    }

    interface Change : View.Change {

    }

    override fun build(): ComponentGroup
}

