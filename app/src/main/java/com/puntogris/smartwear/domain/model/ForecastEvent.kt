package com.puntogris.smartwear.domain.model

import android.content.Context
import com.puntogris.smartwear.R

interface ForecastEvent {
    val summaryRes: Int
    fun buildSummary(context: Context): String
    fun isNotValid(): Boolean = false
}

abstract class EventfulEvent : ForecastEvent {
    abstract val referenceValue: Number
    var eventValue: Number? = null

    override fun buildSummary(context: Context): String {
        return context.getString(summaryRes, eventValue.toString())
    }

    override fun isNotValid(): Boolean = eventValue == null

    abstract fun analyze(hourly: List<Hourly>)
}

class StableEvent : ForecastEvent {
    override val summaryRes: Int = R.string.forecast_stable
    override fun buildSummary(context: Context) = context.getString(summaryRes)
}

class TemperatureRange(private val daily: Daily) : ForecastEvent {
    override val summaryRes: Int = R.string.weather_today_min_max
    override fun buildSummary(context: Context): String {
        return context.getString(summaryRes, daily.min, daily.max)
    }
}

class RainEvent : EventfulEvent() {
    override val referenceValue: Float = 0F
    override val summaryRes: Int = R.string.forecast_precipitation

    override fun analyze(hourly: List<Hourly>) {
        hourly.maxOfOrNull { it.precipitation }?.let {
            if (it > referenceValue) eventValue = it
        }
    }
}

class WindEvent : EventfulEvent() {
    override val referenceValue: Float = 0F
    override val summaryRes: Int = R.string.forecast_wind

    override fun analyze(hourly: List<Hourly>) {
        hourly.maxOfOrNull { it.windSpeed }?.let {
            if (it > referenceValue) eventValue = it
        }
    }
}

class HumidityEvent : EventfulEvent() {
    override val referenceValue: Int = 0
    override val summaryRes: Int = R.string.forecast_humidity

    override fun analyze(hourly: List<Hourly>) {
        hourly.maxOfOrNull { it.humidity }?.let {
            if (it > referenceValue) eventValue = it
        }
    }
}
