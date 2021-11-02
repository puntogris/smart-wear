package com.puntogris.smartwear.domain.model.events

import android.content.Context
import com.puntogris.smartwear.R
import com.puntogris.smartwear.domain.model.WeatherResult
import com.puntogris.smartwear.utils.TimeOfDay

class TemperatureEvent(private val weather: WeatherResult, private val timeOfDay: TimeOfDay) :
    RecommendationEvent() {

    override val summaryRes: Int = R.string.weather_today_min_max
    override val referenceValue: Int = 0
    override val eventValues: List<Int> = weather.hourly.map { it.temperature.toInt() }

    override fun buildSummary(context: Context): String {
        val maxTempSummary = context.getString(
            summaryRes,
            weather.daily.first().min,
            weather.daily.first().max
        )
        val futureTempForecastSummary = getFutureCondition(context, timeOfDay)
        return maxTempSummary + futureTempForecastSummary
    }

    override fun buildRecommendation(context: Context): String {
        val res = when (weather.current.temperature.toInt()) {
            in Int.MIN_VALUE..0 -> R.string.temp_minus_0
            in 1..9 -> R.string.temp_1_9
            in 10..14 -> R.string.temp_10_14
            in 15..19 -> R.string.temp_15_19
            in 20..29 -> R.string.temp_20_29
            in 30..34 -> R.string.temp_30_34
            in 35..39 -> R.string.temp_35_39
            !in 0..40 -> R.string.temp_max_40
            else -> R.string.temp_error
        }
        return context.getString(res)
    }

    private fun getFutureCondition(context: Context, timeOfDay: TimeOfDay): String {
        val first: Int = eventValues.first()
        val middle: Int = eventValues[eventValues.size / 2]
        val last: Int = eventValues.last()

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