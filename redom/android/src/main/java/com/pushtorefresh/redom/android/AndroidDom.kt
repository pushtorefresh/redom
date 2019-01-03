package com.pushtorefresh.redom.android

import com.pushtorefresh.redom.api.Dom
import com.pushtorefresh.redom.api.View

class AndroidDom<O> : Dom<O> {
    override fun build() = TODO()
    override fun <Ob : View.Observe, Ch : View.Change, V : View<O, Ob, Ch>> createDsl(clazz: Class<out V>): V = TODO()
}
