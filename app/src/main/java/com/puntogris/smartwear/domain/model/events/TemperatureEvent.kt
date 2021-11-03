package com.puntogris.smartwear.domain.model.events

import android.content.Context
import com.puntogris.smartwear.R
import com.puntogris.smartwear.common.half
import com.puntogris.smartwear.domain.model.WeatherResult
import com.puntogris.smartwear.utils.TimeOfDay

class TemperatureEvent(
    private val weather: WeatherResult,
    hoursAnalyzed: Int,
    private val timeOfDay: TimeOfDay
) : RecommendationEvent() {

    override val summaryRes: Int = R.string.weather_today_min_max
    override val eventConditions = weather.hourly.subList(0, hoursAnalyzed).map { it.temperature }

    override fun buildSummary(context: Context): String {
        val today = weather.daily.first()
        val maxTempSummary = context.getString(
            summaryRes,
            today.min.asString(),
            today.max.asString()
        )
        val temperatureVariation = getTemperatureForecastVariation(context, timeOfDay)
        return maxTempSummary + temperatureVariation
    }

    override fun buildRecommendation(context: Context): String {
        val res = when (weather.current.temperature.metricValue()) {
            in Int.MIN_VALUE..0 -> R.string.temp_minus_0
            in 1..9 -> R.string.temp_1_9
            in 10..14 -> R.string.temp_10_14
            in 15..19 -> R.string.temp_15_19
            in 20..26 -> R.string.temp_20_29
            in 27..34 -> R.string.temp_27_34
            in 35..39 -> R.string.temp_35_39
            !in 0..40 -> R.string.temp_max_40
            else -> R.string.temp_error
        }
        return context.getString(res)
    }

    private fun getTemperatureForecastVariation(context: Context, timeOfDay: TimeOfDay): String {
        val first: Int = eventConditions.first().metricValue()
        val middle: Int = eventConditions.half().metricValue()
        val last: Int = eventConditions.last().metricValue()

        val res = when {
            middle in (first + 1) until last -> R.string.temp_raise_a_lot
            first < middle || middle < last -> R.string.temp_raise_a_little
            middle in (last + 1) until first -> R.string.temp_decrease_a_lot
            first > middle || middle > last -> R.string.temp_decrease_a_little
            else -> R.string.temp_not_changing
        }

        return context.getString(res) + " " + context.getString(timeOfDay.res)
    }

    override fun isValid(): Boolean = true
}

