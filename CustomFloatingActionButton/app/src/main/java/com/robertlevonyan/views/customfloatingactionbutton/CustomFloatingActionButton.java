package com.robertlevonyan.views.customfloatingactionbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by robert on 3/1/2017.
 */

public class CustomFloatingActionButton extends FrameLayout {

    public static final int FAB_TYPE_CIRCLE = 0;
    public static final int FAB_TYPE_SQUARE = 1;
    public static final int FAB_TYPE_ROUNDED_SQUARE = 2;

    public static final int FAB_SIZE_NORMAL = 10;
    public static final int FAB_SIZE_MINI = 11;

    public static final int FAB_ICON_START = 30;
    public static final int FAB_ICON_TOP = 31;
    public static final int FAB_ICON_END = 32;
    public static final int FAB_ICON_BOTTOM = 33;

    private int fabType;
    private int fabSize;
    private boolean fabMenu;
    private String fabText;
    private boolean fabTextAllCaps;
    private int fabTextColor;
    private float fabElevation;
    private int fabColor;
    private Drawable fabIcon;
    private int fabIconColor;
    private int fabIconPosition;

    private RelativeLayout fabBG;
    private TextView fab;
    private TextView fabShadow;

    private OnFabClickListener onFabClickListener;

    public CustomFloatingActionButton(Context context) {
        this(context, null, 0);
    }

    public CustomFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initTypedArray(attrs);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            buildView();
        }
    }

    private void initTypedArray(AttributeSet attrs) {
        TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomFloatingActionButton, 0, 0);

        fabType = ta.getInt(R.styleable.CustomFloatingActionButton_fabType, FAB_TYPE_CIRCLE);
        fabSize = ta.getInt(R.styleable.CustomFloatingActionButton_fabSizes, FAB_SIZE_NORMAL);
        fabIconPosition = ta.getInt(R.styleable.CustomFloatingActionButton_fabIconPosition, FAB_ICON_START);

        fabText = ta.getString(R.styleable.CustomFloatingActionButton_fabText);
        fabTextAllCaps = ta.getBoolean(R.styleable.CustomFloatingActionButton_fabTextAllCaps, false);
        fabTextColor = ta.getColor(R.styleable.CustomFloatingActionButton_fabTextColor, ContextCompat.getColor(getContext(), R.color.colorFabText));
        fabElevation = ta.getFloat(R.styleable.CustomFloatingActionButton_fabElevation, getResources().getDimension(R.dimen.fab_default_elevation));
        fabColor = ta.getColor(R.styleable.CustomFloatingActionButton_fabColor, ContextCompat.getColor(getContext(), R.color.colorAccent));
        fabIcon = ta.getDrawable(R.styleable.CustomFloatingActionButton_fabIcon);
        fabIconColor = ta.getColor(R.styleable.CustomFloatingActionButton_fabIconColor, ContextCompat.getColor(getContext(), R.color.colorFabIcon));

        if (fabElevation > 15f) {
            fabElevation = 15f;
        }

        ta.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            buildView();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ViewGroup.LayoutParams thisParams = getLayoutParams();

        thisParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        thisParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        setLayoutParams(thisParams);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        buildView();
    }

    private void buildView() {
        initFab();
    }

    private void initFab() {
        initFabBG();
        initShadowView();
        initFabView();


        fabBG.addView(fabShadow);
        fabBG.addView(fab);
        addView(fabBG);
    }

    private void initFabBG() {
        fabBG = new RelativeLayout(getContext());

        LayoutParams fabBGParams = null;

        switch (fabSize) {
            case FAB_SIZE_NORMAL:
                switch (fabIconPosition) {
                    case FAB_ICON_START:
                    case FAB_ICON_END:
                        fabBGParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (getResources().getDimension(R.dimen.fab_size_normal) + fabElevation * 1.2f));
                        break;
                    case FAB_ICON_TOP:
                    case FAB_ICON_BOTTOM:
                        fabBGParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (getResources().getDimension(R.dimen.fab_size_normal) + fabElevation * 1.2f) * 2);
                        break;
                }
                break;
            case FAB_SIZE_MINI:
                switch (fabIconPosition) {
                    case FAB_ICON_START:
                    case FAB_ICON_END:
                        fabBGParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (getResources().getDimension(R.dimen.fab_size_mini) + fabElevation * 1.2f));
                        break;
                    case FAB_ICON_TOP:
                    case FAB_ICON_BOTTOM:
                        fabBGParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (getResources().getDimension(R.dimen.fab_size_mini) + fabElevation * 1.2f) * 2);
                        break;
                }
                break;
            default:
                throw new IllegalStateException("Unrecognized FAB size");
        }

        fabBG.setLayoutParams(fabBGParams);

        fabBG.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFabClickListener != null) {
                    onFabClickListener.onFabClick(v);
                }
            }
        });
    }

    private void initShadowView() {
        if (fabElevation == 0) {
            return;
        }

        fabShadow = new TextView(getContext());

        RelativeLayout.LayoutParams fabShadowParams = null;

        switch (fabSize) {
            case FAB_SIZE_NORMAL:
                switch (fabIconPosition) {
                    case FAB_ICON_START:
                    case FAB_ICON_END:
                        fabShadowParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) getResources().getDimension(R.dimen.fab_size_normal));
                        break;
                    case FAB_ICON_TOP:
                    case FAB_ICON_BOTTOM:
                        fabShadowParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (getResources().getDimension(R.dimen.fab_size_normal) * 1.6));
                        break;
                }
                break;
            case FAB_SIZE_MINI:
                switch (fabIconPosition) {
                    case FAB_ICON_START:
                    case FAB_ICON_END:
                        fabShadowParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) getResources().getDimension(R.dimen.fab_size_mini));
                        break;
                    case FAB_ICON_TOP:
                    case FAB_ICON_BOTTOM:
                        fabShadowParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (getResources().getDimension(R.dimen.fab_size_mini) * 1.6));
                        break;
                }
                break;
            default:
                throw new IllegalStateException("Unrecognized elevation size");
        }

        fabShadow.setLayoutParams(fabShadowParams);
        fabShadow.setTranslationY(fabElevation);
        buildShadowView();
    }

    private void buildShadowView() {
        buildFabShadowBG();
        buildFabShadowIcon();
    }

    private void buildFabShadowBG() {
        Drawable fabBGDrawable;

        switch (fabType) {
            case FAB_TYPE_CIRCLE:
                fabBGDrawable = ContextCompat.getDrawable(getContext(), R.drawable.fab_circle_shadow);
                break;
            case FAB_TYPE_SQUARE:
                fabBGDrawable = ContextCompat.getDrawable(getContext(), R.drawable.fab_square_shadow);
                break;
            case FAB_TYPE_ROUNDED_SQUARE:
                fabBGDrawable = ContextCompat.getDrawable(getContext(), R.drawable.fab_rounded_square_shadow);
                break;
            default:
                fabBGDrawable = null;
                break;
        }

        if (fabBGDrawable != null) {
            fabBGDrawable = CustomFloatingActionButtonUtils.setDrawableColor(fabBGDrawable, fabColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fabShadow.setBackground(fabBGDrawable);
            } else {
                fabShadow.setBackgroundDrawable(fabBGDrawable);
            }
            fabShadow.setGravity(Gravity.CENTER_VERTICAL);
            fabShadow.setTranslationY(fabElevation);
            fabShadow.setAlpha(0.5f);
            if (fabText != null) {
                fabShadow.setText(fabText);
                fabShadow.setAllCaps(fabTextAllCaps);
            }
        } else {
            throw new IllegalStateException("Unable init Floating Action Button");
        }
    }

    private void buildFabShadowIcon() {
        Drawable fabIconDrawable = CustomFloatingActionButtonUtils.setDrawableColor(fabIcon, fabIconColor);

        switch (fabIconPosition) {
            case FAB_ICON_START:
                fabShadow.setCompoundDrawablesWithIntrinsicBounds(fabIconDrawable, null, null, null);
                break;
            case FAB_ICON_TOP:
                fabShadow.setCompoundDrawablesWithIntrinsicBounds(null, fabIconDrawable, null, null);
                break;
            case FAB_ICON_END:
                fabShadow.setCompoundDrawablesWithIntrinsicBounds(null, null, fabIconDrawable, null);
                break;
            case FAB_ICON_BOTTOM:
                fabShadow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, fabIconDrawable);
                break;
            default:
                break;
        }

        if (fabText != null) {
            fabShadow.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen.fab_text_horizontal_margin_normal));
        }

        int padding;
        switch (fabSize) {
            case FAB_SIZE_NORMAL:
                padding = (int) getResources().getDimension(R.dimen.fab_text_horizontal_margin_normal);
                break;
            case FAB_SIZE_MINI:
                padding = (int) getResources().getDimension(R.dimen.fab_text_horizontal_margin_mini);
                break;
            default:
                padding = 0;
                break;
        }

        fabShadow.setPadding(padding, padding, padding, padding);
    }

    private void initFabView() {
        fab = new TextView(getContext());

        RelativeLayout.LayoutParams fabParams = null;

        switch (fabSize) {
            case FAB_SIZE_NORMAL:
                switch (fabIconPosition) {
                    case FAB_ICON_START:
                    case FAB_ICON_END:
                        fabParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) getResources().getDimension(R.dimen.fab_size_normal));
                        break;
                    case FAB_ICON_TOP:
                    case FAB_ICON_BOTTOM:
                        fabParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (getResources().getDimension(R.dimen.fab_size_normal) * 2.2));
                        break;
                }
                break;
            case FAB_SIZE_MINI:
                switch (fabIconPosition) {
                    case FAB_ICON_START:
                    case FAB_ICON_END:
                        fabParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) getResources().getDimension(R.dimen.fab_size_mini));
                        break;
                    case FAB_ICON_TOP:
                    case FAB_ICON_BOTTOM:
                        fabParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (getResources().getDimension(R.dimen.fab_size_mini) * 2.2));
                        break;
                }
                break;
            default:
                throw new IllegalStateException("Unrecognized FAB size");
        }

        fab.setLayoutParams(fabParams);

        buildFabView();
    }

    private void buildFabView() {
        buildFabViewBG();
        buildFabViewIcon();
        buildFabText();
    }

    private void buildFabViewBG() {
        Drawable fabBGDrawable;

        switch (fabType) {
            case FAB_TYPE_CIRCLE:
                fabBGDrawable = ContextCompat.getDrawable(getContext(), R.drawable.fab_circle_bg);
                break;
            case FAB_TYPE_SQUARE:
                fabBGDrawable = ContextCompat.getDrawable(getContext(), R.drawable.fab_square_bg);
                break;
            case FAB_TYPE_ROUNDED_SQUARE:
                fabBGDrawable = ContextCompat.getDrawable(getContext(), R.drawable.fab_rounded_square_bg);
                break;
            default:
                fabBGDrawable = null;
                break;
        }

        if (fabBGDrawable != null) {
            fabBGDrawable = CustomFloatingActionButtonUtils.setDrawableColor(fabBGDrawable, fabColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fab.setBackground(fabBGDrawable);
            } else {
                fab.setBackgroundDrawable(fabBGDrawable);
            }
            fab.setGravity(Gravity.CENTER_VERTICAL);
        } else {
            throw new IllegalStateException("Unable init Floating Action Button");
        }
    }

    private void buildFabViewIcon() {
        Drawable fabIconDrawable = CustomFloatingActionButtonUtils.setDrawableColor(fabIcon, fabIconColor);

        switch (fabIconPosition) {
            case FAB_ICON_START:
                fab.setCompoundDrawablesWithIntrinsicBounds(fabIconDrawable, null, null, null);
                break;
            case FAB_ICON_TOP:
                fab.setCompoundDrawablesWithIntrinsicBounds(null, fabIconDrawable, null, null);
                break;
            case FAB_ICON_END:
                fab.setCompoundDrawablesWithIntrinsicBounds(null, null, fabIconDrawable, null);
                break;
            case FAB_ICON_BOTTOM:
                fab.setCompoundDrawablesWithIntrinsicBounds(null, null, null, fabIconDrawable);
                break;
            default:
                break;
        }

        if (fabText != null) {
            fab.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen.fab_text_horizontal_margin_normal));
        }

        int padding;
        switch (fabSize) {
            case FAB_SIZE_NORMAL:
                padding = (int) getResources().getDimension(R.dimen.fab_text_horizontal_margin_normal);
                break;
            case FAB_SIZE_MINI:
                padding = (int) getResources().getDimension(R.dimen.fab_text_horizontal_margin_mini);
                break;
            default:
                padding = 0;
                break;
        }

        fab.setPadding(padding, padding, padding, padding);
    }

    private void buildFabText() {
        if (fabText == null) {
            return;
        }

        fab.setText(fabText);
        fab.setTextColor(fabTextColor);
        fab.setSingleLine();
        fab.setEllipsize(TextUtils.TruncateAt.END);
        fab.setAllCaps(fabTextAllCaps);
    }

    public void setFabType(int fabType) {
        if (fabType >= FAB_TYPE_CIRCLE && fabType <= FAB_TYPE_ROUNDED_SQUARE) {
            this.fabType = fabType;
            return;
        }
        this.fabType = FAB_TYPE_CIRCLE;
    }

    public void setFabSize(int fabSize) {
        if (fabSize >= FAB_SIZE_NORMAL && fabSize <= FAB_SIZE_MINI) {
            this.fabSize = fabSize;
            return;
        }
        this.fabSize = FAB_SIZE_NORMAL;
    }

    public void setFabMenu(boolean fabMenu) {
        this.fabMenu = fabMenu;
    }

    public void setFabText(String fabText) {
        this.fabText = fabText;
    }

    public void setFabTextAllCaps(boolean fabTextAllCaps) {
        this.fabTextAllCaps = fabTextAllCaps;
    }

    public void setFabTextColor(int fabTextColor) {
        this.fabTextColor = fabTextColor;
    }

    public void setFabElevation(float fabElevation) {
        this.fabElevation = fabElevation;
        if (this.fabElevation > 15f) {
            this.fabElevation = 15f;
        }
    }

    public void setFabColor(int fabColor) {
        this.fabColor = fabColor;
    }

    public int getFabType() {
        return fabType;
    }

    public int getFabSize() {
        return fabSize;
    }

    public boolean isFabMenu() {
        return fabMenu;
    }

    public String getFabText() {
        return fabText;
    }

    public boolean isFabTextAllCaps() {
        return fabTextAllCaps;
    }

    public int getFabTextColor() {
        return fabTextColor;
    }

    public float getFabElevation() {
        return fabElevation;
    }

    public int getFabColor() {
        return fabColor;
    }

    public Drawable getFabIcon() {
        return fabIcon;
    }

    public void setFabIcon(Drawable fabIcon) {
        this.fabIcon = fabIcon;
    }

    public int getFabIconColor() {
        return fabIconColor;
    }

    public void setFabIconColor(int fabIconColor) {
        this.fabIconColor = fabIconColor;
    }

    public void setFabIconPosition(int position) {
        if (position >= FAB_ICON_START && position <= FAB_ICON_BOTTOM) {
            fabIconPosition = position;
            return;
        }
        fabIconPosition = FAB_ICON_START;
    }

    public void setOnFabClickListener(OnFabClickListener onFabClickListener) {
        this.onFabClickListener = onFabClickListener;
    }
}
