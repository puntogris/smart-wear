package com.puntogris.smartwear.domain.model

import com.puntogris.smartwear.domain.model.events.ForecastEvent
import com.puntogris.smartwear.presentation.util.TimeOfDay

class Forecast(
    val events: List<ForecastEvent>,
    val time: TimeOfDay
)