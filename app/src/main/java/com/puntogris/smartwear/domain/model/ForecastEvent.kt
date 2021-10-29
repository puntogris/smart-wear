package com.puntogris.smartwear.domain.model

import android.content.Context
import com.puntogris.smartwear.R

interface ForecastEvent {
    val summaryRes: Int
    fun buildSummary(context: Context): String
    fun isValid(): Boolean = true
}

class StableEvent : ForecastEvent {
    override val summaryRes: Int = R.string.forecast_stable
    override fun buildSummary(context: Context) = context.getString(summaryRes)
}

class TemperatureEvent(private val daily: Daily) : ForecastEvent {
    override val summaryRes: Int = R.string.weather_today_min_max
    override fun buildSummary(context: Context): String {
        return context.getString(summaryRes, daily.min, daily.max)
    }
}

abstract class EventfulEvent : ForecastEvent {
    abstract val referenceValue: Int
    abstract val eventValues: List<Int>
    abstract val increaseLowRes: Int
    abstract val increaseHighRes: Int
    abstract val decreaseLowRes: Int
    abstract val decreaseHighRes: Int
    abstract val maintainRes: Int

    private val first: Int
        get() = eventValues.first()

    private val middle: Int
        get() = eventValues[eventValues.size / 2]

    private val last: Int
        get() = eventValues.last()

    private val eventValue: Int
        get() = eventValues.maxOrNull() ?: 0

    override fun buildSummary(context: Context): String {
        return context.getString(summaryRes, eventValue.toString())
    }

    override fun isValid(): Boolean = eventValue > referenceValue

    fun buildRecommendation(context: Context): String {
        val res = when {
            middle in (first + 1) until last -> increaseHighRes
            first < middle || middle < last -> increaseLowRes
            middle in (last + 1) until first -> decreaseHighRes
            first > middle || middle > last -> decreaseLowRes
            else -> maintainRes
        }
        return context.getString(res)
    }
}

class RainEvent(hourly: List<Hourly>) : EventfulEvent() {
    override val referenceValue: Int = 0
    override val increaseLowRes: Int = R.string.weather_today_min_max
    override val increaseHighRes: Int = R.string.weather_today_min_max
    override val decreaseLowRes: Int = R.string.weather_today_min_max
    override val decreaseHighRes: Int = R.string.weather_today_min_max
    override val maintainRes: Int = R.string.weather_today_min_max
    override val summaryRes: Int = R.string.forecast_precipitation
    override val eventValues = hourly.map { it.precipitation.toInt() }
}

class WindEvent(hourly: List<Hourly>) : EventfulEvent() {
    override val referenceValue: Int = 0
    override val increaseLowRes: Int = R.string.weather_today_min_max
    override val increaseHighRes: Int = R.string.weather_today_min_max
    override val decreaseLowRes: Int = R.string.weather_today_min_max
    override val decreaseHighRes: Int = R.string.weather_today_min_max
    override val maintainRes: Int = R.string.weather_today_min_max
    override val summaryRes: Int = R.string.forecast_wind
    override val eventValues = hourly.map { it.windSpeed.toInt() }
}

class HumidityEvent(hourly: List<Hourly>) : EventfulEvent() {
    override val referenceValue: Int = 0
    override val increaseLowRes: Int = R.string.weather_today_min_max
    override val increaseHighRes: Int = R.string.weather_today_min_max
    override val decreaseLowRes: Int = R.string.weather_today_min_max
    override val decreaseHighRes: Int = R.string.weather_today_min_max
    override val maintainRes: Int = R.string.weather_today_min_max
    override val summaryRes: Int = R.string.forecast_humidity
    override val eventValues = hourly.map { it.humidity}
}
