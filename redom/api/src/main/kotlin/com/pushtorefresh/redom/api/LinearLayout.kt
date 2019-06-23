package com.pushtorefresh.redom.api

import com.pushtorefresh.redom.api.LayoutParams.Size
import com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.NO_GRAVITY

interface LinearLayout : ViewGroup {

    var orientation: Orientation

    interface LayoutParams : com.pushtorefresh.redom.api.LayoutParams {

        val weight: Float

        val gravity: Gravity

        enum class Gravity {
            NO_GRAVITY,
            TOP,
            BOTTOM,
            LEFT,
            RIGHT,
            START,
            END,
            CENTER_VERTICAL,
            FILL_VERTICAL,
            CENTER_HORIZONTAL,
            FILL_HORIZONTAL,
            CENTER,
            FILL
        }

        private data class Impl(
            override val weight: Float,
            override val gravity: LinearLayout.LayoutParams.Gravity,
            override val width: Size,
            override val height: Size,
            override val marginStart: Size.Scalar?,
            override val marginEnd: Size.Scalar?,
            override val marginTop: Size.Scalar?,
            override val marginBottom: Size.Scalar?
        ) : LinearLayout.LayoutParams

        companion object {
            fun create(
                width: Size,
                height: Size,
                weight: Float = 0F,
                gravity: LinearLayout.LayoutParams.Gravity = NO_GRAVITY,
                marginStart: Size.Scalar? = null,
                marginEnd: Size.Scalar? = null,
                marginTop: Size.Scalar? = null,
                marginBottom: Size.Scalar? = null
            ): LayoutParams {
                return Impl(
                    width = width,
                    height = height,
                    gravity = gravity,
                    weight = weight,
                    marginStart = marginStart,
                    marginEnd = marginEnd,
                    marginTop = marginTop,
                    marginBottom = marginBottom
                )
            }

        }

    }

    enum class Orientation {
        Vertical,
        Horizontal,

    }
}

fun ViewParent.LinearLayout(lambda: LinearLayout.() -> Unit): Unit =
    lambda(createView(LinearLayout::class.java))
