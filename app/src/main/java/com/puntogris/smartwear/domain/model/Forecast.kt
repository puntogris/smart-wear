package com.puntogris.smartwear.domain.model

import com.puntogris.smartwear.utils.TimeOfDay

class Forecast(
    val events: List<ForecastEvent>,
    val time: TimeOfDay
)