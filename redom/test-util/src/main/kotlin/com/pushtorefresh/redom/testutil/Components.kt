package com.pushtorefresh.redom.testutil

import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.ComponentGroup
import com.pushtorefresh.redom.api.View
import com.pushtorefresh.redom.api.ViewGroup
import io.reactivex.Observable
import java.lang.IllegalArgumentException

fun <T : View<*, *, *>> createComponent(clazz: Class<T>): Component<Any, Any> {
    if (ViewGroup::class.java.isAssignableFrom(clazz)) {
        throw IllegalArgumentException("createComponent can't be used with ViewGroup, use createComponentGroup")
    }

    return object : Component<Any, Any> {
        override val clazz: Class<out View<*, *, *>> = clazz

        override val output: Observable<Any>
            get() = throw IllegalAccessError()
        override val rawOutput: Observable<*>
            get() = throw IllegalAccessError()

        override fun bind(view: Any) = throw IllegalAccessError()
    }
}

fun <T : ViewGroup<*, *, *>> createComponentGroup(clazz: Class<T>, children: List<Component<Any, Any>>): ComponentGroup<Any, Any> {
    return object : ComponentGroup<Any, Any> {
        override val clazz: Class<out ViewGroup<*, *, *>> = clazz

        override val children: List<Component<Any, out Any>> = children

        override val output: Observable<Any>
            get() = throw IllegalAccessError()
        override val rawOutput: Observable<*>
            get() = throw IllegalAccessError()

        override fun bind(view: Any) = throw IllegalAccessError()
    }

}
