package com.github.ming13.oktwitter.view

import android.content.*
import android.support.annotation.*
import android.util.*
import android.widget.ViewAnimator

class ViewAnimator : ViewAnimator
{
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
    }

    fun show(@IdRes viewId: Int) {
        if (getChildAt(displayedChild).id == viewId) {
            return
        }

        for (childViewPosition in 0..childCount) {
            if (getChildAt(childViewPosition).id == viewId) {
                displayedChild = childViewPosition

                return
            }
        }

        val viewIdName = resources.getResourceEntryName(viewId)

        throw IllegalArgumentException("View with ID $viewIdName is not found.")
    }
}