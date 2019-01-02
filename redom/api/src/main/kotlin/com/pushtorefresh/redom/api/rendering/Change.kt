// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.api.rendering

/**
 * Change to be rendered.
 *
 * [Renderer] might use [equals] and [hashCode] to compare [Change]s and optimize rendering pipeline based on that.
 */
interface Change {

    /**
     * Called once by [Renderer] on appropriate thread (by default on Main Thread).
     */
    fun perform()
}
