package com.robertlevonyan.views.customfloatingactionbutton

enum class FabIconPosition {
    FAB_ICON_START, FAB_ICON_TOP, FAB_ICON_END, FAB_ICON_BOTTOM;

    companion object {
        fun getByIndex(index: Int): FabIconPosition {
            return when (index) {
                FAB_ICON_TOP.ordinal -> FAB_ICON_TOP
                FAB_ICON_END.ordinal -> FAB_ICON_END
                FAB_ICON_BOTTOM.ordinal -> FAB_ICON_BOTTOM
                else -> FAB_ICON_START
            }
        }
    }
}