package com.pushtorefresh.redom.android

import com.pushtorefresh.redom.android.view.AndroidViewParent
import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Dom
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewParent

internal class AndroidDom(private val viewParent: ViewParent) : Dom {

    private val views = mutableListOf<View>()

    override fun <V : View> createView(clazz: Class<out V>): V =
        viewParent.createView(clazz).also { views += it }

    override fun build(): List<BaseComponent<*, *>> =
        views.map(View::build)

}

fun <O : Any> androidDom(viewParent: ViewParent = AndroidViewParent(), builder: Dom.() -> Unit): Dom {
    return AndroidDom(viewParent).apply(builder)
}
