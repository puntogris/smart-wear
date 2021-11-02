package com.puntogris.smartwear.domain.model.events

import com.puntogris.smartwear.R
import com.puntogris.smartwear.data.data_source.Condition
import com.puntogris.smartwear.domain.model.WeatherResult

class WindEvent(weatherResult: WeatherResult, hoursAnalyzed: Int) : DetailedEvent() {

    override val summaryRes: Int = R.string.forecast_wind

    override val metricReferenceValue: Int = 0

    override val eventValues: List<Condition> =
        weatherResult.hourly.subList(0, hoursAnalyzed).map { it.windSpeed }

    override val getMaxCondition: Condition? = eventValues.maxByOrNull { it.metricValue() }
}

