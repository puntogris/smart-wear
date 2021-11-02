package com.puntogris.smartwear.domain.model.events

import android.content.Context
import com.puntogris.smartwear.R
import com.puntogris.smartwear.domain.model.Hourly

class RainEvent(private val hourly: List<Hourly>) : RecommendationEvent() {

    override val referenceValue: Int = 14
    override val summaryRes: Int = R.string.forecast_precipitation

    override val eventValues: List<Int> = hourly.map { it.precipitation.toInt() }

    override fun buildRecommendation(context: Context): String {
        val current = hourly.first().precipitation.toInt()

        val res = when (current) {
            in 14..59 -> R.string.precipitation_15_60
            else -> R.string.precipitation_60
        }
        return context.getString(res)
    }
}
