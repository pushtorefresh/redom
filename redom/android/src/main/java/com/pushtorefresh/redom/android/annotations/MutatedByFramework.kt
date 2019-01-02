// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.android.annotations

import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.PROPERTY

/**
 * Means that property needs to be observed in order to keep state in sync between Android Framework and Redom.
 * Also means that there is no point in making property lazy.
 */
@Target(PROPERTY)
@Retention(SOURCE)
internal annotation class MutatedByFramework
