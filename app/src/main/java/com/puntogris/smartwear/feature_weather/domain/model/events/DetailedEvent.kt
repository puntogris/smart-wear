package com.puntogris.smartwear.feature_weather.domain.model.events

import android.content.Context
import com.puntogris.smartwear.feature_weather.domain.model.conditions.WeatherCondition

abstract class DetailedEvent : ForecastEvent {

    abstract val eventConditions: List<WeatherCondition>
    open val metricReferenceValue: Int = 0

    open val getMaxCondition: WeatherCondition? = null

    override fun buildSummary(context: Context): String {
        return context.getString(
            summaryRes,
            getMaxCondition?.asString()
        )
    }

    override fun isValid(): Boolean = getMaxCondition?.metricValue() ?: 0 > metricReferenceValue
}