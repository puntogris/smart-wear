package com.puntogris.smartwear.feature_weather.domain.model.conditions

import com.puntogris.smartwear.core.utils.constants.Constants

sealed class Wind(value: Int) : WeatherCondition(value) {

    class MetersPerSecond(value: Int) : Wind(value) {
        override fun asString(): String = "$value m/s"
        override fun metricValue(): Int = this.value
    }

    class Mph(value: Int) : Wind(value) {
        override fun asString(): String = "$value mph"
        override fun metricValue(): Int = (value / 2.237).toInt()
    }

    companion object {
        fun from(value: Float, unit: String): Wind {
            return when (unit) {
                Constants.METRIC -> MetersPerSecond(value.toInt())
                else -> Mph(value.toInt())
            }
        }
    }
}