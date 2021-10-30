package com.puntogris.smartwear.domain.model.events

import com.puntogris.smartwear.R
import com.puntogris.smartwear.domain.model.Hourly

class HumidityEvent(hourly: List<Hourly>) : DetailedEvent() {

    override val referenceValue: Int = 0
    override val summaryRes: Int = R.string.forecast_humidity

    override val eventValues: List<Int> = hourly.map { it.humidity }
}
