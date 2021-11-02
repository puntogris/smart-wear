package com.puntogris.smartwear.domain.model.events

import com.puntogris.smartwear.R
import com.puntogris.smartwear.domain.model.WeatherResult
import com.puntogris.smartwear.domain.model.conditions.WeatherCondition

class HumidityEvent(weatherResult: WeatherResult, hoursAnalyzed: Int) : DetailedEvent() {

    override val summaryRes: Int = R.string.forecast_humidity

    override val metricReferenceValue: Int = 0

    override val eventConditions: List<WeatherCondition> =
        weatherResult.hourly.subList(0, hoursAnalyzed).map { it.humidity }

    override val getMaxCondition: WeatherCondition? = eventConditions.maxByOrNull { it.metricValue() }
}
