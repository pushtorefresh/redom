package com.pushtorefresh.redom.api

interface ViewGroup<O, Ob: ViewGroup.Observe, Ch: ViewGroup.Change> : Dom<O> {

    interface Observe : View.Observe {

    }

    interface Change : View.Change {

    }

    override fun build(): List<Component>
}

