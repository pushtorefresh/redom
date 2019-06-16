package com.pushtorefresh.redom.android.recycler

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.pushtorefresh.redom.api.LayoutParams

class AndroidLayoutParamsFactory(
    private val context: Context
) : (LayoutParams?) -> ViewGroup.LayoutParams? {

    override fun invoke(layoutParams: com.pushtorefresh.redom.api.LayoutParams?): ViewGroup.LayoutParams? {
        return when (layoutParams) {
            is com.pushtorefresh.redom.api.LinearLayout.LayoutParams -> createLinearLayoutLP(layoutParams)
            is LayoutParams -> createMarginLP(layoutParams)
            else -> null
        }
    }

    private fun createMarginLP(layoutParams: LayoutParams): ViewGroup.MarginLayoutParams {
        val width: Int = layoutParams.width.toAndroid()
        val height: Int = layoutParams.height.toAndroid()
        val marginBottom: Int = layoutParams.marginBottom?.toAndroid() ?: 0
        val marginTop: Int = layoutParams.marginTop?.toAndroid() ?: 0
        val marginStart: Int = layoutParams.marginStart?.toAndroid() ?: 0
        val marginEnd: Int = layoutParams.marginEnd?.toAndroid() ?: 0
        return ViewGroup.MarginLayoutParams(width, height).apply {
            this.bottomMargin = marginBottom
            this.topMargin = marginTop
            this.leftMargin = marginStart
            this.rightMargin = marginEnd
        }
    }

    private fun createLinearLayoutLP(
        layoutParams: com.pushtorefresh.redom.api.LinearLayout.LayoutParams
    ): LinearLayout.LayoutParams {
        val width: Int = layoutParams.width.toAndroid()
        val height: Int = layoutParams.height.toAndroid()
        val gravity = when (layoutParams.gravity) {
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.NO_GRAVITY -> Gravity.NO_GRAVITY
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.TOP -> Gravity.TOP
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.BOTTOM -> Gravity.BOTTOM
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.LEFT -> Gravity.LEFT
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.RIGHT -> Gravity.RIGHT
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.START -> Gravity.START
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.END -> Gravity.END
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.CENTER_VERTICAL -> Gravity.CENTER_VERTICAL
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.FILL_VERTICAL -> Gravity.FILL_VERTICAL
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.CENTER_HORIZONTAL -> Gravity.CENTER_HORIZONTAL
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.FILL_HORIZONTAL -> Gravity.FILL_HORIZONTAL
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.CENTER -> Gravity.CENTER
            com.pushtorefresh.redom.api.LinearLayout.LayoutParams.Gravity.FILL -> Gravity.FILL
        }
        val marginBottom: Int = layoutParams.marginBottom?.toAndroid() ?: 0
        val marginTop: Int = layoutParams.marginTop?.toAndroid() ?: 0
        val marginStart: Int = layoutParams.marginStart?.toAndroid() ?: 0
        val marginEnd: Int = layoutParams.marginEnd?.toAndroid() ?: 0
        return LinearLayout.LayoutParams(width, height, layoutParams.weight).apply {
            this.gravity = gravity
            this.bottomMargin = marginBottom
            this.topMargin = marginTop
            this.leftMargin = marginStart
            this.rightMargin = marginEnd
        }
    }

    private fun LayoutParams.Size.Scalar.toAndroid(): Int {
        return when (this) {
            is LayoutParams.Size.Scalar.Dp -> toAndroidDp()
            is LayoutParams.Size.Scalar.Px -> toAndroidPx()
        }
    }

    private fun LayoutParams.Size.toAndroid(): Int {
        return when (this) {
            is LayoutParams.Size.WrapContent -> ViewGroup.LayoutParams.WRAP_CONTENT
            is LayoutParams.Size.MatchParent -> ViewGroup.LayoutParams.MATCH_PARENT
            is LayoutParams.Size.Scalar -> toAndroid()
        }
    }

    /**
     * todo display metricas can work wrong with multiple displays
     */
    private fun LayoutParams.Size.Scalar.Dp.toAndroidDp(): Int {
        return TypedValue
            .applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.value.toFloat(),
                context.resources.displayMetrics
            )
            .toInt()
    }

    /**
     * todo display metricas can work wrong with multiple displays
     */
    private fun LayoutParams.Size.Scalar.Px.toAndroidPx(): Int {
        return TypedValue
            .applyDimension(
                TypedValue.COMPLEX_UNIT_PX,
                this.value.toFloat(),
                context.resources.displayMetrics
            )
            .toInt()
    }
}