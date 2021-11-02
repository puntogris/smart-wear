package com.puntogris.smartwear.domain.model.events

import android.content.Context

abstract class DetailedEvent: ForecastEvent {
    abstract val eventValues: List<Int>
    abstract val referenceValue: Int

    private val eventValue: Int
        get() = eventValues.maxOrNull() ?: 0

    override fun buildSummary(context: Context): String {
        return context.getString(summaryRes, eventValue.toString())
    }

    override fun isValid(): Boolean = eventValue > referenceValue
}