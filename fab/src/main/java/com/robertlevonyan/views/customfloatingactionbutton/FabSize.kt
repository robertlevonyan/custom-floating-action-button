package com.robertlevonyan.views.customfloatingactionbutton

enum class FabSize {
    FAB_SIZE_NORMAL, FAB_SIZE_MINI;

    companion object {
        fun getByIndex(index: Int): FabSize {
            return when (index) {
                FAB_SIZE_MINI.ordinal -> FAB_SIZE_MINI
                else -> FAB_SIZE_NORMAL
            }
        }
    }
}