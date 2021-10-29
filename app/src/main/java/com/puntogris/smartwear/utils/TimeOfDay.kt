package com.puntogris.smartwear.utils

import org.threeten.bp.LocalTime

sealed class TimeOfDay {
    object Morning : TimeOfDay()
    object Afternoon : TimeOfDay()
    object Evening : TimeOfDay()
    object Night : TimeOfDay()

    companion object {
        fun withOffset(hoursOffset: Int): TimeOfDay {
            return when (LocalTime.now().hour + hoursOffset) {
                in 6..11 -> Morning
                in 12..16 -> Afternoon
                in 17..21 -> Evening
                else -> Night
            }
        }
    }
}