package com.puntogris.smartwear.domain.model.events

import android.content.Context

interface ForecastEvent {
    val summaryRes: Int
    fun buildSummary(context: Context): String
    fun isValid(): Boolean
}

