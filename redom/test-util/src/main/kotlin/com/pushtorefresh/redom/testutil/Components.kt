package com.pushtorefresh.redom.testutil

import com.pushtorefresh.redom.api.BaseComponent
import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.ComponentGroup
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewGroup

fun <T : View> createComponent(clazz: Class<T>): Component<View, Any> {
    if (ViewGroup::class.java.isAssignableFrom(clazz)) {
        throw IllegalArgumentException("createComponent can't be used with ViewGroup, use createComponentGroup")
    }

    return Component(
        binder = { _, _ -> TODO() },
        dslView = object : View {
            override var enabled: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var onClick: (() -> Unit)?
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}

            override fun build(): BaseComponent<out View, out Any> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        },
        clazz = clazz
    )
}

fun <T : ViewGroup> createComponentGroup(
    clazz: Class<T>,
    children: List<BaseComponent<out View, Any>>
): ComponentGroup<ViewGroup, Any> {
    return ComponentGroup(
        binder = { _, _, _ -> TODO() },
        dslView = object : ViewGroup {
            override var enabled: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var onClick: (() -> Unit)?
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}

            override fun build(): BaseComponent<out View, out Any> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun <V : View> createView(clazz: Class<out V>): V {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        },
        clazz = clazz,
        children = children
    )

}
