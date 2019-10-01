package com.pushtorefresh.redom.android

import android.view.View
import com.pushtorefresh.redom.api.IdRegistry

class AndroidIdRegistry<IdType> : IdRegistry<IdType> {

    private val ids = mutableMapOf<IdType?, Int>()

    override fun mapToInt(id: IdType?): Int = ids[id] ?: generateInt(id).also { ids[id] = it }

    private fun generateInt(id: IdType?): Int = if (id == null) View.NO_ID else View.generateViewId()
}
