package com.pushtorefresh.redom.api

interface LayoutParams {
    val width: Size
    val height: Size
    val marginStart: Size.Scalar?
    val marginEnd: Size.Scalar?
    val marginTop: Size.Scalar?
    val marginBottom: Size.Scalar?

    sealed class Size {
        object MatchParent : Size()
        object WrapContent : Size()
        sealed class Scalar : Size() {
            data class Dp(val value: Int) : Scalar()
            data class Px(val value: Int) : Scalar()
        }
    }

    private data class LayoutParamsImpl(
        override val width: LayoutParams.Size,
        override val height: LayoutParams.Size,
        override val marginStart: LayoutParams.Size.Scalar?,
        override val marginEnd: LayoutParams.Size.Scalar?,
        override val marginTop: LayoutParams.Size.Scalar?,
        override val marginBottom: LayoutParams.Size.Scalar?
    ) : LayoutParams

    companion object {
        fun create(
            width: LayoutParams.Size,
            height: LayoutParams.Size,
            marginStart: LayoutParams.Size.Scalar? = null,
            marginEnd: LayoutParams.Size.Scalar? = null,
            marginTop: LayoutParams.Size.Scalar? = null,
            marginBottom: LayoutParams.Size.Scalar? = null
        ): LayoutParams {
            return LayoutParamsImpl(
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







