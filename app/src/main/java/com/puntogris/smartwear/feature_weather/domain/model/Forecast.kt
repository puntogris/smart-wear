package com.puntogris.smartwear.feature_weather.domain.model

import com.puntogris.smartwear.feature_weather.domain.model.events.ForecastEvent
import com.puntogris.smartwear.feature_weather.presentation.util.TimeOfDay

class Forecast(
    val events: List<ForecastEvent>,
    val time: TimeOfDay
)