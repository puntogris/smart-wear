package com.puntogris.smartwear.feature_weather.domain.model.events

import android.content.Context

interface ForecastEvent {
    val summaryRes: Int
    fun buildSummary(context: Context): String
    fun isValid(): Boolean
}

