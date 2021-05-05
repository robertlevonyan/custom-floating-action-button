package com.robertlevonyan.views.customfloatingactionbutton

enum class FabType {
    FAB_TYPE_CIRCLE, FAB_TYPE_SQUARE, FAB_TYPE_ROUNDED_SQUARE;

    companion object {
        fun getByIndex(index: Int): FabType {
            return when (index) {
                FAB_TYPE_SQUARE.ordinal -> FAB_TYPE_SQUARE
                FAB_TYPE_ROUNDED_SQUARE.ordinal -> FAB_TYPE_ROUNDED_SQUARE
                else -> FAB_TYPE_CIRCLE
            }
        }
    }
}