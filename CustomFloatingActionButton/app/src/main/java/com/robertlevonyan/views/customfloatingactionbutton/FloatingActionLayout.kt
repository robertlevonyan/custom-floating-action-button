package com.robertlevonyan.views.customfloatingactionbutton

import android.content.Context
import android.graphics.*
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat

class FloatingActionLayout : FrameLayout {
    var fabType = FabType.FAB_TYPE_CIRCLE
        set(value) {
            field = value
            buildView()
        }
    var fabElevation = 0f
        set(value) {
            field = value
            buildView()
        }
    var fabColor = 0
        set(value) {
            field = value
            buildView()
        }
    var fabRippleColor = 0
        set(value) {
            field = value
            buildView()
        }

    private val clipPath = Path()
    private val roundedSquareRect = RectF()

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let { initTypedArray(it) } ?: initDefaultValues()
    }

    private fun initTypedArray(attrs: AttributeSet) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, 0, 0)

        fabType = FabType.getByIndex(ta.getInt(R.styleable.FloatingActionButton_fabType, FabType.FAB_TYPE_CIRCLE.ordinal))
        fabElevation = ta.getDimension(R.styleable.FloatingActionButton_fabElevation, resources.getDimension(R.dimen.fab_default_elevation))
        fabColor = ta.getColor(R.styleable.FloatingActionButton_fabColor, ContextCompat.getColor(context, R.color.colorAccent))
        fabRippleColor = ta.getColor(R.styleable.FloatingActionButton_fabRippleColor, ContextCompat.getColor(context, R.color.colorPrimary))

        ta.recycle()
    }

    private fun initDefaultValues() {
        fabType = FabType.FAB_TYPE_CIRCLE
        fabElevation = resources.getDimension(R.dimen.fab_default_elevation)
        fabColor = ContextCompat.getColor(context, R.color.colorAccent)
        fabRippleColor = ContextCompat.getColor(context, R.color.colorPrimary)
    }

    private fun buildView() {
        if (childCount > 1) throw IllegalStateException("Floating Action Layout must have only one direct child")

        createElevation()
        createBackground()
    }

    private fun createElevation() {
        ViewCompat.setElevation(this, fabElevation)
    }

    private fun createBackground() {
        val background = ContextCompat.getDrawable(context, when (fabType) {
            FabType.FAB_TYPE_SQUARE -> R.drawable.fab_square_bg
            FabType.FAB_TYPE_ROUNDED_SQUARE -> R.drawable.fab_rounded_square_bg
            else -> R.drawable.fab_circle_bg
        })?.mutate()?.apply {
            mutate().colorFilter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                BlendModeColorFilter(fabColor, BlendMode.SRC_IN)
            } else {
                PorterDuffColorFilter(fabColor, PorterDuff.Mode.SRC_IN)
            }
        } ?: return

        val selectableBackground = createSelectableBackground(background, fabRippleColor)
        setBackground(LayerDrawable(arrayOf(background, selectableBackground)))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (fabType == FabType.FAB_TYPE_CIRCLE) {
            minimumHeight = resources.getDimensionPixelSize(R.dimen.fab_size_normal)
            minimumWidth = resources.getDimensionPixelSize(R.dimen.fab_size_normal)

            val width = measuredWidth
            val height = measuredHeight
            val squareLen = if (height > width) height else width

            setMeasuredDimension(squareLen, squareLen)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let { cnv ->
            when (fabType) {
                FabType.FAB_TYPE_CIRCLE -> {
                    clipPath.addCircle(measuredWidth / 2f, measuredHeight / 2f, measuredWidth / 2f, Path.Direction.CW)
                    cnv.clipPath(clipPath)
                }
                FabType.FAB_TYPE_ROUNDED_SQUARE -> {
                    val radius = resources.getDimension(R.dimen.fab_rounded_radius)
                    roundedSquareRect.apply { left = 0f; top = 0f; right = measuredWidth.toFloat(); bottom = measuredHeight.toFloat() }
                    clipPath.addRoundRect(roundedSquareRect, radius, radius, Path.Direction.CW)
                    cnv.clipPath(clipPath)
                }
                else -> {
                    roundedSquareRect.apply { left = 0f; top = 0f; right = measuredWidth.toFloat(); bottom = measuredHeight.toFloat() }
                    clipPath.addRect(roundedSquareRect, Path.Direction.CW)
                    cnv.clipPath(clipPath)
                }
            }
        }
    }
}