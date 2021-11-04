package com.puntogris.smartwear.feature_weather.domain.model.events

import com.puntogris.smartwear.R
import com.puntogris.smartwear.feature_weather.domain.model.WeatherResult
import com.puntogris.smartwear.feature_weather.domain.model.conditions.WeatherCondition

class WindEvent(weatherResult: WeatherResult, hoursAnalyzed: Int) : DetailedEvent() {

    override val summaryRes: Int = R.string.forecast_wind

    override val metricReferenceValue: Int = 0

    override val eventConditions: List<WeatherCondition> =
        weatherResult.hourly.subList(0, hoursAnalyzed).map { it.windSpeed }

    override val getMaxCondition: WeatherCondition? = eventConditions.maxByOrNull { it.metricValue() }
}

