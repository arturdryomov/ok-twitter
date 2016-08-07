package com.github.ming13.oktwitter.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.ming13.oktwitter.R

class RecyclerViewDividerDecoration(context: Context) : RecyclerView.ItemDecoration()
{
    private val divider: Drawable

    init {
        this.divider = ContextCompat.getDrawable(context, R.drawable.bg_divider)
    }

    override fun onDrawOver(canvas: Canvas, recyclerView: RecyclerView, recyclerViewState: RecyclerView.State?) {
        super.onDrawOver(canvas, recyclerView, recyclerViewState)

        drawDividers(canvas, recyclerView)
    }

    private fun drawDividers(canvas: Canvas, recyclerView: RecyclerView) {
        val borderLeft = recyclerView.paddingLeft
        val borderRight = recyclerView.width - recyclerView.paddingRight

        for (childViewPosition in 0..recyclerView.childCount - 1) {
            val childView = recyclerView.getChildAt(childViewPosition)

            val childViewParams = childView.layoutParams as RecyclerView.LayoutParams

            val borderTop = childView.bottom + childViewParams.bottomMargin + childView.translationY.toInt()
            val borderBottom = borderTop + divider.intrinsicHeight

            divider.alpha = (255 * childView.alpha).toInt()
            divider.setBounds(borderLeft, borderTop, borderRight, borderBottom)
            divider.draw(canvas)
        }
    }

    override fun getItemOffsets(rectangle: Rect, view: View, recyclerView: RecyclerView, recyclerViewState: RecyclerView.State?) {
        super.getItemOffsets(rectangle, view, recyclerView, recyclerViewState)

        rectangle.set(0, 0, 0, divider.intrinsicHeight)
    }
}