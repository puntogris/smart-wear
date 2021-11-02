package com.puntogris.smartwear.domain.model.events

import android.content.Context
import com.puntogris.smartwear.R
import com.puntogris.smartwear.data.data_source.Condition
import com.puntogris.smartwear.domain.model.WeatherResult

class RainEvent(private val weatherResult: WeatherResult, hoursAnalyzed: Int) :
    RecommendationEvent() {

    override val metricReferenceValue: Int = 14

    override val summaryRes: Int = R.string.forecast_precipitation

    override val eventValues: List<Condition> =
        weatherResult.hourly.subList(0, hoursAnalyzed).map { it.precipitation }

    override val getMaxCondition: Condition? = eventValues.maxByOrNull { it.value }

    override fun buildRecommendation(context: Context): String {
        val current = weatherResult.hourly.first().precipitation.metricValue()

        val res = when (current) {
            in 14..59 -> R.string.precipitation_15_60
            else -> R.string.precipitation_60
        }
        return context.getString(res)
    }

}
