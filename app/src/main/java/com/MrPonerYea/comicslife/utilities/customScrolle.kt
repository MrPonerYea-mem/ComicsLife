package com.MrPonerYea.comicslife.utilities

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView


class CustomScrollView : NestedScrollView {
    var isEnableScrolling = true

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(context: Context) : super(context) {}

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (isEnableScrolling) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {

        return if (isEnableScrolling) {
            super.onTouchEvent(ev)
        } else {
            false
        }
    }

    override fun performClick(): Boolean {
        return if (isEnableScrolling) {
            super.performClick()
        } else {
            false
        }
    }
}