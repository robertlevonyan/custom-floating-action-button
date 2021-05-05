package com.robertlevonyan.views.customfloatingactionbutton

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup

internal fun createSelectableBackground(background: Drawable, color: Int): Drawable {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        RippleDrawable(ColorStateList.valueOf(color), background, null)
    } else {
        StateListDrawable().apply {
            setExitFadeDuration(400)
            alpha = 45
            addState(intArrayOf(android.R.attr.state_pressed), ColorDrawable(color))
            addState(intArrayOf(), ColorDrawable(Color.TRANSPARENT))
        }
    }
}

internal operator fun ViewGroup.get(index: Int): View? = getChildAt(index)

fun FloatingLayout.doOnExpand(onExpand: () -> Unit) {
    onMenuExpandedListener = object : FloatingLayout.OnMenuExpandedListener {
        override fun onMenuExpanded() {
            onExpand()
        }

        override fun onMenuCollapsed() {
        }

    }
}

fun FloatingLayout.doOnCollapse(onCollapse: () -> Unit) {
    onMenuExpandedListener = object : FloatingLayout.OnMenuExpandedListener {
        override fun onMenuExpanded() {
        }

        override fun onMenuCollapsed() {
            onCollapse()
        }

    }
}