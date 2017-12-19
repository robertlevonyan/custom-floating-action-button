package com.robertlevonyan.views.customfloatingactionbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by robertlevonyan on 12/7/17.
 */

public class FloatingActionLayout extends FrameLayout {
    public static final int FAB_TYPE_CIRCLE = 0;
    public static final int FAB_TYPE_SQUARE = 1;
    public static final int FAB_TYPE_ROUNDED_SQUARE = 2;

    private int fabType;
    private float fabElevation;
    private int fabColor;

    private boolean isCreated;

    private Path clipPath;
    private RectF roundedSquareRect;

    public FloatingActionLayout(@NonNull Context context) {
        this(context, null, 0);
    }

    public FloatingActionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initTypedArray(attrs);
        clipPath = new Path();
    }

    private void initTypedArray(AttributeSet attrs) {
        TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, 0, 0);

        fabType = ta.getInt(R.styleable.FloatingActionButton_fabType, FAB_TYPE_CIRCLE);
        fabElevation = ta.getDimension(R.styleable.FloatingActionButton_fabElevation, getResources().getDimension(R.dimen.fab_default_elevation));
        fabColor = ta.getColor(R.styleable.FloatingActionButton_fabColor, ContextCompat.getColor(getContext(), R.color.colorAccent));

        ta.recycle();
    }

    private void buildView() {
        if (getChildCount() > 1) {
            throw new IllegalStateException("Floating Action Layout must have only one direct child");
        }
        isCreated = true;
        initFabBackground();
        initFabShadow();

        requestLayout();
    }

    private void initFabBackground() {
        Drawable backgroundDrawable;
        switch (fabType) {
            case FAB_TYPE_SQUARE:
                backgroundDrawable = ContextCompat.getDrawable(getContext(), R.drawable.fab_square_bg);
                break;
            case FAB_TYPE_ROUNDED_SQUARE:
                backgroundDrawable = ContextCompat.getDrawable(getContext(), R.drawable.fab_rounded_square_bg);
                break;
            default:
                backgroundDrawable = ContextCompat.getDrawable(getContext(), R.drawable.fab_circle_bg);
                break;
        }

        backgroundDrawable.mutate().setColorFilter(fabColor, PorterDuff.Mode.SRC_IN);

        Drawable selectableDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            selectableDrawable = new RippleDrawable(ColorStateList.valueOf(Color.argb(150, 255, 255, 255)),
                    null, backgroundDrawable);
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.setExitFadeDuration(400);
            stateListDrawable.setAlpha(45);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.argb(150, 255, 255, 255)));
            stateListDrawable.addState(new int[]{}, new ColorDrawable(Color.TRANSPARENT));

            selectableDrawable = stateListDrawable;
        }

        LayerDrawable backgroundLayers = new LayerDrawable(new Drawable[]{
                backgroundDrawable,
                selectableDrawable});

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(backgroundLayers);
        } else {
            setBackgroundDrawable(backgroundLayers);
        }
    }

    private void initFabShadow() {
        ViewCompat.setElevation(this, fabElevation);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.fab_size_normal));
        setMinimumWidth(getResources().getDimensionPixelSize(R.dimen.fab_size_normal));

        if (fabType == FAB_TYPE_CIRCLE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            int squareLen;
            if (height > width) {
                squareLen = height;
            } else {
                squareLen = width;
            }
            super.onMeasure(MeasureSpec.makeMeasureSpec(squareLen, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(squareLen, MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isCreated) {
            buildView();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!isCreated) {
            buildView();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (fabType == FAB_TYPE_CIRCLE) {
            clipPath.addCircle(getMeasuredWidth() / 2f, getMeasuredHeight() / 2f, getMeasuredWidth() / 2f , Path.Direction.CW);
            canvas.clipPath(clipPath);
        } else if (fabType == FAB_TYPE_ROUNDED_SQUARE) {
            float radius = getResources().getDimension(R.dimen.fab_rounded_radius);
            roundedSquareRect = new RectF(0f, 0f, getMeasuredWidth(), getMeasuredHeight());
            clipPath.addRoundRect(roundedSquareRect, radius, radius, Path.Direction.CW);
            canvas.clipPath(clipPath);
        }
    }
}