package com.robertlevonyan.views.customfloatingactionbutton

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Property
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import java.util.*

class FloatingLayout : FrameLayout {
    var fabAnimationStyle = FabMenuAnimation.ANIMATION_POP_UP
    var fabAnimateMenu = false
        set(value) {
            field = value
            buildView()
        }
    var fabMenuGravity = Gravity.TOP
        set(value) {
            field = value
            buildView()
        }
    var fabAnimateDuration = 300
        set(value) {
            field = value
            buildView()
        }

    private var isAnimating = false
    private var isExpanded = false

    private val views = ArrayList<View>()

    var onMenuExpandedListener: OnMenuExpandedListener? = null

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initTypedArray(attrs)
    }

    private fun initTypedArray(attrs: AttributeSet?) {
        if (attrs == null) return

        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, 0, 0)

        fabAnimationStyle = FabMenuAnimation.getByIndex(ta.getInt(R.styleable.FloatingActionButton_fabMenuStyle, FabMenuAnimation.ANIMATION_POP_UP.ordinal))
        fabMenuGravity = ta.getInt(R.styleable.FloatingActionButton_android_gravity, Gravity.TOP)
        fabAnimateMenu = ta.getBoolean(R.styleable.FloatingActionButton_fabAnimateMenu, true)
        fabAnimateDuration = ta.getInt(R.styleable.FloatingActionButton_fabAnimateDuration, 300)

        ta.recycle()
    }

    private fun buildView() {
        if (childCount == 0) return

        createViewStack()
        createMargins()
        createAnimations()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        buildView()
    }

    private fun createViewStack() {
        for (i in 0 until childCount) {
            this[i]?.let { childView ->
                if (childView is FloatingActionButton || childView is com.google.android.material.floatingactionbutton.FloatingActionButton) {
                    if (i == 0) {
                        when (childView) {
                            is FloatingActionButton -> childView.fabElevation = 11f
                            is com.google.android.material.floatingactionbutton.FloatingActionButton -> childView.compatElevation = 11f
                        }
                    } else {
                        childView.visibility = View.GONE
                        when (childView) {
                            is FloatingActionButton -> childView.fabElevation = 9f
                            is com.google.android.material.floatingactionbutton.FloatingActionButton -> childView.compatElevation = 9f
                        }
                    }

                    childView.layoutParams = (childView.layoutParams as LayoutParams).apply {
                        gravity = fabMenuGravity
                    }
                    views.add(childView)
                } else {
                    removeView(childView)
                }
            }
        }
    }

    private fun createMargins() {
        if (views.isEmpty()) return

        views.forEach {
            val lp = it.layoutParams as LayoutParams
            if (it is FloatingActionButton) {
                val margin = resources.getDimensionPixelSize(R.dimen.default_margin)
                if (it.fabSize == FabSize.FAB_SIZE_NORMAL) {
                    lp.setMargins(margin, margin, margin, margin)
                } else {
                    val marginFormMini = resources.getDimensionPixelSize(R.dimen.default_margin_for_mini)
                    when (fabAnimationStyle) {
                        FabMenuAnimation.ANIMATION_POP_DOWN -> lp.setMargins(marginFormMini, margin, marginFormMini, margin)
                        FabMenuAnimation.ANIMATION_POP_RIGHT -> lp.setMargins(margin, marginFormMini, margin, marginFormMini)
                        FabMenuAnimation.ANIMATION_POP_LEFT -> lp.setMargins(margin, marginFormMini, margin, marginFormMini)
                        else -> lp.setMargins(marginFormMini, margin, marginFormMini, margin)
                    }
                }
            }
        }
    }

    private fun createAnimations() {
        if (views.isEmpty()) return

        views[0].setOnClickListener {
            if (isAnimating) return@setOnClickListener
            if (isExpanded) collapse() else expand()
        }
    }

    private fun collapse() {
        when (fabAnimationStyle) {
            FabMenuAnimation.ANIMATION_POP_LEFT -> collapseDirection(View.TRANSLATION_X, -1)
            FabMenuAnimation.ANIMATION_POP_UP -> collapseDirection(View.TRANSLATION_Y, -1)
            FabMenuAnimation.ANIMATION_POP_RIGHT -> collapseDirection(View.TRANSLATION_X, 1)
            FabMenuAnimation.ANIMATION_POP_DOWN -> collapseDirection(View.TRANSLATION_Y, 1)
        }

        if (fabAnimateMenu) {
            ObjectAnimator.ofFloat(views[0], View.ROTATION, 45f, 0f)
                    .apply { duration = fabAnimateDuration.toLong() }.start()
        }
    }

    private fun collapseDirection(viewProperty: Property<View, Float>, directionFactor: Int) {
        isAnimating = true
        val animators = ArrayList<Animator>()

        for (i in views.size - 1 downTo 1) {
            val view = views[i]
            val animationSize = 160f
            val viewAnimator = ObjectAnimator.ofFloat(view, viewProperty, directionFactor * i * animationSize, 0f)
                    .apply {
                        addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                view.visibility = View.GONE
                            }
                        })
                    }
            animators.add(viewAnimator)
        }
        AnimatorSet().apply {
            duration = fabAnimateDuration.toLong()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    isAnimating = false
                    isExpanded = !isExpanded
                    onMenuExpandedListener?.onMenuCollapsed()
                }
            })
            playSequentially(animators)
        }.start()
    }

    private fun expand() {
        when (fabAnimationStyle) {
            FabMenuAnimation.ANIMATION_POP_LEFT -> expandDirection(View.TRANSLATION_X, -1)
            FabMenuAnimation.ANIMATION_POP_UP -> expandDirection(View.TRANSLATION_Y, -1)
            FabMenuAnimation.ANIMATION_POP_RIGHT -> expandDirection(View.TRANSLATION_X, 1)
            FabMenuAnimation.ANIMATION_POP_DOWN -> expandDirection(View.TRANSLATION_Y, 1)
        }

        if (fabAnimateMenu) {
            ObjectAnimator.ofFloat(views[0], View.ROTATION, 0f, 45f).start()
        }
    }

    private fun expandDirection(viewProperty: Property<View, Float>, directionFactor: Int) {
        isAnimating = true
        val animators = ArrayList<Animator>()

        for (i in views.size - 1 downTo 1) {
            val view = views[i]
            val animationSize = 160f
            val viewAnimator = ObjectAnimator.ofFloat(view, viewProperty, 0f, directionFactor * i * animationSize)
                    .apply {
                        addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationStart(animation: Animator?) {
                                super.onAnimationStart(animation)
                                view.visibility = View.VISIBLE
                            }
                        })
                    }
            animators.add(viewAnimator)
        }
        AnimatorSet().apply {
            duration = fabAnimateDuration.toLong()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    isAnimating = false
                    isExpanded = !isExpanded
                    onMenuExpandedListener?.onMenuExpanded()
                }
            })
            playSequentially(animators)
        }.start()
    }

    interface OnMenuExpandedListener {
        fun onMenuExpanded()

        fun onMenuCollapsed()
    }
}