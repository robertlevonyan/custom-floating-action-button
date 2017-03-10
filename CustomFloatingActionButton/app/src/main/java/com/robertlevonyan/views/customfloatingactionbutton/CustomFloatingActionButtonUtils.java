package com.robertlevonyan.views.customfloatingactionbutton;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

/**
 * Created by robert on 3/1/2017.
 */

class CustomFloatingActionButtonUtils {

    public static final int FAB_ID = 0x0000180591;
    public static final int FAB_ICON_ID = 0x0000911805;

    static Drawable setDrawableColor(Drawable icon, int color) {
        if (icon == null) {
            return null;
        }
        icon.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        return icon;
    }

}
