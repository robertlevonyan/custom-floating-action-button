package com.robertlevonyan.views.customfloatingactionbutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by robert on 3/1/2017.
 */

class FloatingActionButtonUtils {

    public static final int FAB_ID = 0x0000180591;
    public static final int FAB_ICON_ID = 0x0000911805;

    static Drawable setDrawableColor(Drawable icon, int color) {
        icon.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        return icon;
    }

    static Drawable resize(Context context, Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, (int) context.getResources().getDimension(R.dimen.fab_icon_size), (int) context.getResources().getDimension(R.dimen.fab_icon_size), false);
        return new BitmapDrawable(context.getResources(), bitmapResized);
    }

}
