package com.pushtorefresh.redom.android

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pushtorefresh.redom.api.ImageLoader
import com.pushtorefresh.redom.api.ImageView
import java.io.Closeable

class GlideImageLoader(private val context: Context) : ImageLoader<Drawable> {
    override fun load(drawable: ImageView.Drawable, listener: (Drawable) -> Unit): Closeable {
        val request = Glide.with(context)
            .createImageRequest(drawable)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    listener(resource)
                    return true
                }

            }).submit()

        return Closeable { request.cancel(true) }
    }

    private fun RequestManager.createImageRequest(drawable: ImageView.Drawable): RequestBuilder<Drawable> {
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
}