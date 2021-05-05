package com.robertlevonyan.views.customfloatingactionbutton

enum class FabMenuAnimation {
    ANIMATION_POP_UP, ANIMATION_POP_DOWN, ANIMATION_POP_RIGHT, ANIMATION_POP_LEFT;

    companion object {
        fun getByIndex(index: Int): FabMenuAnimation {
            return when (index) {
                ANIMATION_POP_DOWN.ordinal -> ANIMATION_POP_DOWN
                ANIMATION_POP_RIGHT.ordinal -> ANIMATION_POP_RIGHT
                ANIMATION_POP_LEFT.ordinal -> ANIMATION_POP_LEFT
                else -> ANIMATION_POP_UP
            }
        }
    }
}