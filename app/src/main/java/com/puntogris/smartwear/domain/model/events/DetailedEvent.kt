package com.puntogris.smartwear.domain.model.events

import android.content.Context
import com.puntogris.smartwear.R

abstract class DetailedEvent: ForecastEvent {
    abstract val eventValues: List<Int>
    abstract val referenceValue: Int

    private val eventValue: Int
        get() = eventValues.maxOrNull() ?: 0

    override fun buildSummary(context: Context): String {
        return context.getString(R.string.forecast_wind, eventValue.toString())
    }

    override fun isValid(): Boolean = eventValue > referenceValue
}