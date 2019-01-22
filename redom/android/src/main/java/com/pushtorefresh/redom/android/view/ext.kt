package com.pushtorefresh.redom.android.view

@Suppress("UNCHECKED_CAST")
internal inline fun <K, V, VV : V> MutableMap<K, V>.getOrPutAndGet(key: K, factory: () -> VV): VV =
        this[key] as VV ?: factory().also { this[key] = it as V }
