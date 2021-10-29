package com.puntogris.smartwear.domain.model

import android.content.Context
import com.puntogris.smartwear.R

abstract class ForecastEvent {
    abstract val summaryRes: Int
    abstract fun buildSummary(context: Context): String
    abstract fun isNotValid():Boolean
}

abstract class EventfulEvent(open val value: Number) : ForecastEvent() {
    abstract val referenceValue: Number

    override fun buildSummary(context: Context): String {
        return context.getString(summaryRes, value.toString())
    }

    override fun isNotValid(): Boolean = referenceValue.toFloat() > value.toFloat()
}

class StableEvent : ForecastEvent() {
    override val summaryRes: Int = R.string.forecast_stable
    override fun buildSummary(context: Context) = context.getString(summaryRes)
    override fun isNotValid(): Boolean = false
}

class TemperatureRange(private val daily: Daily) : ForecastEvent() {
    override val summaryRes: Int = R.string.weather_today_min_max
    override fun buildSummary(context: Context): String {
        return context.getString(summaryRes, daily.min, daily.max)
    }
    override fun isNotValid(): Boolean = false
}

class RainEvent(override val value: Float) : EventfulEvent(value) {
    override val referenceValue: Float = 0F
    override val summaryRes: Int = R.string.forecast_precipitation
}

class WindEvent(override val value: Float) : EventfulEvent(value) {
    override val referenceValue: Float = 0F
    override val summaryRes: Int = R.string.forecast_wind
}

class HumidityEvent(override val value: Int) : EventfulEvent(value) {
    override val referenceValue: Int = 0
    override val summaryRes: Int = R.string.forecast_humidity
}
