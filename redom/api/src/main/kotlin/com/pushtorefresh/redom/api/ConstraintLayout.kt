package com.pushtorefresh.redom.api

interface ConstraintLayout : ViewGroup {

    interface LayoutParams : com.pushtorefresh.redom.api.LayoutParams {

        val baselineToBaseline: String?
        val bottomToBottom: String?
        val bottomToTop: String?
        val endToEnd: String?
        val endToStart: String?
        val startToEnd: String?
        val startToStart: String?
        val topToBottom: String?
        val topToTop: String?

        private data class Impl(
            override val baselineToBaseline: String?,
            override val bottomToBottom: String?,
            override val bottomToTop: String?,
            override val endToEnd: String?,
            override val endToStart: String?,
            override val startToEnd: String?,
            override val startToStart: String?,
            override val topToBottom: String?,
            override val topToTop: String?,
            override val width: com.pushtorefresh.redom.api.LayoutParams.Size,
            override val height: com.pushtorefresh.redom.api.LayoutParams.Size,
            override val marginStart: com.pushtorefresh.redom.api.LayoutParams.Size.Scalar?,
            override val marginEnd: com.pushtorefresh.redom.api.LayoutParams.Size.Scalar?,
            override val marginTop: com.pushtorefresh.redom.api.LayoutParams.Size.Scalar?,
            override val marginBottom: com.pushtorefresh.redom.api.LayoutParams.Size.Scalar?
        ) : ConstraintLayout.LayoutParams

        companion object {
            fun create(
                baselineToBaseline: String? = null,
                bottomToBottom: String? = null,
                bottomToTop: String? = null,
                endToEnd: String? = null,
                endToStart: String? = null,
                startToEnd: String? = null,
                startToStart: String? = null,
                topToBottom: String? = null,
                topToTop: String? = null,
                width: com.pushtorefresh.redom.api.LayoutParams.Size = com.pushtorefresh.redom.api.LayoutParams.Size.Scalar.Dp(0),
                height: com.pushtorefresh.redom.api.LayoutParams.Size = com.pushtorefresh.redom.api.LayoutParams.Size.Scalar.Dp(0),
                marginStart: com.pushtorefresh.redom.api.LayoutParams.Size.Scalar? = null,
                marginEnd: com.pushtorefresh.redom.api.LayoutParams.Size.Scalar? = null,
                marginTop: com.pushtorefresh.redom.api.LayoutParams.Size.Scalar? = null,
                marginBottom: com.pushtorefresh.redom.api.LayoutParams.Size.Scalar? = null
            ): LayoutParams {
                return Impl(
                    baselineToBaseline = baselineToBaseline,
                    bottomToBottom = bottomToBottom,
                    bottomToTop = bottomToTop,
                    endToEnd = endToEnd,
                    endToStart = endToStart,
                    startToEnd = startToEnd,
                    startToStart = startToStart,
                    topToBottom = topToBottom,
                    topToTop = topToTop,
                    width = width,
                    height = height,
                    marginStart = marginStart,
                    marginEnd = marginEnd,
                    marginTop = marginTop,
                    marginBottom = marginBottom
                )
            }
        }

    }

}

fun ViewParent.ConstraintLayout(lambda: ConstraintLayout.() -> Unit): Unit =
    lambda(createView(ConstraintLayout::class.java))

