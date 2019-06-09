package com.pushtorefresh.redom.android.view

import com.pushtorefresh.redom.api.Component
import com.pushtorefresh.redom.api.ViewStructure
import com.pushtorefresh.redom.api.toViewStructure

internal abstract class DefaultComponent<O: Any, V: Any> : Component<O, V> {

    private var _viewStructure: ViewStructure? = null

    final override val viewStructure: ViewStructure
        get() = _viewStructure ?: (toViewStructure(this).also { _viewStructure = it })
}
