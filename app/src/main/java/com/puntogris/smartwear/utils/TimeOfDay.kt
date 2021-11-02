package com.puntogris.smartwear.utils

import com.puntogris.smartwear.R
import org.threeten.bp.LocalTime

sealed class TimeOfDay(val res: Int) {
    class Morning(res: Int) : TimeOfDay(res)
    class Afternoon(res: Int) : TimeOfDay(res)
    class Evening(res: Int) : TimeOfDay(res)
    class Night(res: Int) : TimeOfDay(res)

    companion object {
        fun withOffset(hoursOffset: Int): TimeOfDay {
            return when (LocalTime.now().hour + hoursOffset) {
                in 6..11 -> Morning(R.string.morning)
                in 12..16 -> Afternoon(R.string.afternoon)
                in 17..21 -> Evening(R.string.evening)
                else -> Night(R.string.night)
            }
        }
    }
}