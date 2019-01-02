package com.pushtorefresh.redom.api

interface ComponentGroup : Component {
    val children: List<Component>
}
