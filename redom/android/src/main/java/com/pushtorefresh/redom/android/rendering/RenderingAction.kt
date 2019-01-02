// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.android.rendering

import com.pushtorefresh.redom.api.rendering.Change
import io.reactivex.Observable

/**
 * Abstracts object property change and implements equals in a way that matches pro
 */
internal abstract class AndroidChange(private val obj: Any, private val propertyId: Int) : Change {

    override fun equals(other: Any?) = other is AndroidChange
            &&
            other.obj === this.obj
            &&
            other.propertyId == this.propertyId

    override fun hashCode() = obj.hashCode() + propertyId

    override fun toString() = "AndroidChange(obj = ${obj.javaClass}(${obj.hashCode()}), propertyId = $propertyId)"
}

internal inline fun <T> Observable<T>.mapToChange(obj: Any, propertyId: Int, crossinline func: (T) -> Unit): Observable<out Change> = map { value ->
    object : AndroidChange(obj, propertyId) {
        override fun perform() {
            func.invoke(value)
        }
    }
}
