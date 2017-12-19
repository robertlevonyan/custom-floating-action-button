package com.robertlevonyan.views.customfloatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;

/**
 * Created by robertlevonyan on 11/20/17.
 */

public class FloatingLayout extends FrameLayout {
    public static final int ANIMATION_POP_UP = 20;
    public static final int ANIMATION_POP_DOWN = 21;
    public static final int ANIMATION_POP_RIGHT = 22;
    public static final int ANIMATION_POP_LEFT = 23;

    public static final int MENU_GRAVITY_START = 0;
    public static final int MENU_GRAVITY_END = 1;
    public static final int MENU_GRAVITY_BOTTOM = 2;
    public static final int MENU_GRAVITY_TOP = 4;
    public static final int MENU_GRAVITY_CENTER_HORIZONTAL = 8;
    public static final int MENU_GRAVITY_CENTER_VERTICAL = 16;
    public static final int MENU_GRAVITY_CENTER = 31;

    private int fabAnimationStyle;
    private int fabMenuGravity;
    private boolean fabAnimateMenu;

    private OnMenuExpandedListener onMenuExpandedListener;

    private boolean isCreated;
    private boolean isExpanded;
    private ArrayList<View> views = new ArrayList<>();

    private boolean isGravityStart;
    private boolean isGravityEnd;
    private boolean isGravityBottom;
    private boolean isGravityTop;
    private boolean isGravityCenterHorizontal;
    private boolean isGravityCenterVertical;
    private boolean isGravityCenter;

    public FloatingLayout(@NonNull Context context) {
        this(context, null, 0);
    }

    public FloatingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initTypedArray(attrs);
    }

    private void initTypedArray(AttributeSet attrs) {
        TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, 0, 0);

        fabAnimationStyle = ta.getInt(R.styleable.FloatingActionButton_fabMenuStyle, ANIMATION_POP_UP);
        fabMenuGravity = ta.getInt(R.styleable.FloatingActionButton_fabMenuGravity, MENU_GRAVITY_TOP);
        fabAnimateMenu = ta.getBoolean(R.styleable.FloatingActionButton_fabAnimateMenu, true);

        isGravityStart = containsFlag(fabMenuGravity, MENU_GRAVITY_START);
        isGravityEnd = containsFlag(fabMenuGravity, MENU_GRAVITY_END);
        isGravityBottom = containsFlag(fabMenuGravity, MENU_GRAVITY_BOTTOM);
        isGravityTop = containsFlag(fabMenuGravity, MENU_GRAVITY_TOP);
        isGravityCenterHorizontal = containsFlag(fabMenuGravity, MENU_GRAVITY_CENTER_HORIZONTAL);
        isGravityCenterVertical = containsFlag(fabMenuGravity, MENU_GRAVITY_CENTER_VERTICAL);
        isGravityCenter = containsFlag(fabMenuGravity, MENU_GRAVITY_CENTER);

        ta.recycle();
    }

    private boolean containsFlag(int flagSet, int flag) {
        return (flagSet | flag) == flagSet;
    }

    private void buildView() {
        isCreated = true;
        if (getChildCount() == 0) {
            return;
        }

        stackViews();
        initMargins();
        initViewAnimation();
    }

    private void stackViews() {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);

            if (childView instanceof FloatingActionButton
                    || childView instanceof android.support.design.widget.FloatingActionButton) {
                if (i == 0) {
                    if (childView instanceof FloatingActionButton) {
                        FloatingActionButton fab = (FloatingActionButton) childView;
                        fab.setFabElevation(11);
                    } else if (childView instanceof android.support.design.widget.FloatingActionButton) {
                        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) childView;
                        fab.setCompatElevation(11);
                    }
                } else {
                    childView.setVisibility(GONE);
                    if (childView instanceof FloatingActionButton) {
                        FloatingActionButton fab = (FloatingActionButton) childView;
                        fab.setFabElevation(9);
                    } else if (childView instanceof android.support.design.widget.FloatingActionButton) {
                        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) childView;
                        fab.setCompatElevation(9);
                    }
                }

                childView.setLayoutParams(getGravity(childView));

                views.add(childView);
            } else {
                removeView(childView);
            }
        }
    }

    private ViewGroup.LayoutParams getGravity(View childView) {
        FrameLayout.LayoutParams lp = (LayoutParams) childView.getLayoutParams();
        if (isGravityCenter) {
            lp.gravity = Gravity.CENTER;
        } else if (isGravityBottom) {
            if (isGravityEnd) {
                lp.gravity = Gravity.BOTTOM | Gravity.END;
            } else if (isGravityCenterHorizontal) {
                lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            } else {
                lp.gravity = Gravity.BOTTOM;
            }
        } else if (isGravityEnd) {
            if (isGravityCenterVertical) {
                lp.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
            } else {
                lp.gravity = Gravity.TOP | Gravity.END;
            }
        } else if (isGravityTop) {
            lp.gravity = Gravity.TOP;
        } else if (isGravityCenterHorizontal) {
            lp.gravity = Gravity.CENTER_HORIZONTAL;
        } else if (isGravityCenterVertical) {
            lp.gravity = Gravity.CENTER_VERTICAL;
        }
        return lp;
    }

    private void initMargins() {
        View toggleView = views.get(0);
        FrameLayout.LayoutParams toggleLp = (LayoutParams) toggleView.getLayoutParams();
        for (View view : views) {
            FrameLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            if (view instanceof FloatingActionButton) {
                FloatingActionButton fab = (FloatingActionButton) view;
                int margin = getResources().getDimensionPixelSize(R.dimen.default_margin);
                if (fab.getFabSize() == FloatingActionButton.FAB_SIZE_NORMAL) {
                    lp.setMargins(margin, margin, margin, margin);
                } else {
                    int marginFormMini = getResources().getDimensionPixelSize(R.dimen.default_margin_for_mini);
                    switch (fabAnimationStyle) {
                        case ANIMATION_POP_DOWN:
                            lp.setMargins(marginFormMini, margin, marginFormMini, margin);
                            break;
                        case ANIMATION_POP_RIGHT:
                            lp.setMargins(margin, marginFormMini, margin, marginFormMini);
                            break;
                        case ANIMATION_POP_LEFT:
                            lp.setMargins(margin, marginFormMini, margin, marginFormMini);
                            break;
                        default:
                            lp.setMargins(marginFormMini, margin, marginFormMini, margin);
                            break;
                    }
                }
            }
        }
    }

    private void initViewAnimation() {
        View toggleView = views.get(0);
        toggleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded) {
                    collapseViews();
                } else {
                    expandViews();
                }
                isExpanded = !isExpanded;
            }
        });
    }

    private void collapseViews() {
        switch (fabAnimationStyle) {
            case ANIMATION_POP_DOWN:
                animatePopDownCollapse();
                break;
            case ANIMATION_POP_RIGHT:
                animatePopRightCollapse();
                break;
            case ANIMATION_POP_LEFT:
                animatePopLeftCollapse();
                break;
            default:
                animatePopUpCollapse();
                break;
        }

        if (fabAnimateMenu) {
            View toggleView = views.get(0);
            ObjectAnimator collapseAnimator = ObjectAnimator.ofFloat(toggleView, "rotation", 45f, 0f);
            collapseAnimator.start();
        }
    }

    private void animatePopDownCollapse() {
        ArrayList<Animator> animators = new ArrayList<>();
        for (int i = views.size() - 1; i > 0; i--) {
            final View view = views.get(i);
            float animationSize = 160f;

            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationY", i * animationSize, 0f);
            viewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(GONE);
                }
            });
            animators.add(viewAnimator);
        }

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (onMenuExpandedListener != null) {
                    onMenuExpandedListener.onMenuCollapsed();
                }
            }
        });

        set.start();
    }

    private void animatePopRightCollapse() {
        ArrayList<Animator> animators = new ArrayList<>();
        for (int i = views.size() - 1; i > 0; i--) {
            final View view = views.get(i);
            float animationSize = 160f;

            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationX", i * animationSize, 0f);
            viewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(GONE);
                }
            });
            animators.add(viewAnimator);
        }

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (onMenuExpandedListener != null) {
                    onMenuExpandedListener.onMenuCollapsed();
                }
            }
        });

        set.start();
    }

    private void animatePopLeftCollapse() {
        ArrayList<Animator> animators = new ArrayList<>();
        for (int i = views.size() - 1; i > 0; i--) {
            final View view = views.get(i);
            float animationSize = 160f;

            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationX", -i * animationSize, 0f);
            viewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(GONE);
                }
            });
            animators.add(viewAnimator);
        }

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (onMenuExpandedListener != null) {
                    onMenuExpandedListener.onMenuCollapsed();
                }
            }
        });

        set.start();
    }

    private void animatePopUpCollapse() {
        ArrayList<Animator> animators = new ArrayList<>();
        for (int i = views.size() - 1; i > 0; i--) {
            final View view = views.get(i);
            float animationSize = 160f;

            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationY", -i * animationSize, 0f);
            viewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(GONE);
                }
            });
            animators.add(viewAnimator);
        }

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (onMenuExpandedListener != null) {
                    onMenuExpandedListener.onMenuCollapsed();
                }
            }
        });

        set.start();
    }

    private void expandViews() {
        switch (fabAnimationStyle) {
            case ANIMATION_POP_DOWN:
                animatePopDownExpand();
                break;
            case ANIMATION_POP_RIGHT:
                animatePopRightExpand();
                break;
            case ANIMATION_POP_LEFT:
                animatePopLeftExpand();
                break;
            default:
                animatePopUpExpand();
                break;
        }
        if (fabAnimateMenu) {
            View toggleView = views.get(0);
            ObjectAnimator expandAnimator = ObjectAnimator.ofFloat(toggleView, "rotation", 0f, 45f);
            expandAnimator.start();
        }
    }

    private void animatePopDownExpand() {
        ArrayList<Animator> animators = new ArrayList<>();
        for (int i = 1; i < views.size(); i++) {
            final View view = views.get(i);
            float animationSize = 160f;

            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationY", 0f, i * animationSize);
            viewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    view.setVisibility(VISIBLE);
                }
            });
            animators.add(viewAnimator);
        }

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (onMenuExpandedListener != null) {
                    onMenuExpandedListener.onMenuExpanded();
                }
            }
        });

        set.start();
    }

    private void animatePopRightExpand() {
        ArrayList<Animator> animators = new ArrayList<>();
        for (int i = 1; i < views.size(); i++) {
            final View view = views.get(i);
            float animationSize = 160f;

            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationX", 0f, i * animationSize);
            viewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    view.setVisibility(VISIBLE);
                }
            });
            animators.add(viewAnimator);
        }

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (onMenuExpandedListener != null) {
                    onMenuExpandedListener.onMenuExpanded();
                }
            }
        });

        set.start();
    }

    private void animatePopLeftExpand() {
        ArrayList<Animator> animators = new ArrayList<>();
        for (int i = 1; i < views.size(); i++) {
            final View view = views.get(i);
            float animationSize = 160f;

            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationX", 0f, -i * animationSize);
            viewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    view.setVisibility(VISIBLE);
                }
            });
            animators.add(viewAnimator);
        }

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (onMenuExpandedListener != null) {
                    onMenuExpandedListener.onMenuExpanded();
                }
            }
        });

        set.start();
    }

    private void animatePopUpExpand() {
        ArrayList<Animator> animators = new ArrayList<>();
        for (int i = 1; i < views.size(); i++) {
            final View view = views.get(i);
            float animationSize = 160f;

            ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "translationY", 0f, -i * animationSize);
            viewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    view.setVisibility(VISIBLE);
                }
            });
            animators.add(viewAnimator);
        }

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (onMenuExpandedListener != null) {
                    onMenuExpandedListener.onMenuExpanded();
                }
            }
        });

        set.start();
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

    public void setOnMenuExpandedListener(OnMenuExpandedListener onMenuExpandedListener) {
        this.onMenuExpandedListener = onMenuExpandedListener;
    }

    public int getFabAnimationStyle() {
        return fabAnimationStyle;
    }

    public void setFabAnimationStyle(int fabAnimationStyle) {
        this.fabAnimationStyle = fabAnimationStyle;
    }

    public int getFabMenuGravity() {
        return fabMenuGravity;
    }

    public void setFabMenuGravity(int fabMenuGravity) {
        this.fabMenuGravity = fabMenuGravity;
    }

    public interface OnMenuExpandedListener {
        void onMenuExpanded();

        void onMenuCollapsed();
    }
}
