package com.pushtorefresh.redom.android.view

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.pushtorefresh.redom.api.ImageView

fun RequestManager.createImageRequest(drawable: ImageView.Drawable): RequestBuilder<Drawable> {
    val requestBuilder = when (drawable) {
        is ImageView.Drawable.Resource -> {
            load(drawable.resourceId)
        }
        is ImageView.Drawable.Http -> {
            val requestBuilder = load(drawable.url)
            drawable.placeholderId?.let {
                requestBuilder.placeholder(it)
            }

            requestBuilder
        }
        else -> throw IllegalArgumentException("unsupported drawable ${drawable::class.java}")
    }
    drawable.scaleType?.let {
        when (it) {
            ImageView.ScaleType.CenterCrop -> {
                requestBuilder.centerCrop()
            }
            ImageView.ScaleType.CenterInside -> {
                requestBuilder.centerInside()
            }
            ImageView.ScaleType.FitCenter -> {
                requestBuilder.fitCenter()
            }
        }
    }
    return requestBuilder
}