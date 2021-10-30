package com.puntogris.smartwear.domain.model.events

import com.puntogris.smartwear.R
import com.puntogris.smartwear.domain.model.Hourly

class WindEvent(hourly: List<Hourly>) : DetailedEvent() {

    override val referenceValue: Int = 0
    override val summaryRes: Int = R.string.forecast_wind

    override val eventValues: List<Int> = hourly.map { it.windSpeed.toInt() }
}

