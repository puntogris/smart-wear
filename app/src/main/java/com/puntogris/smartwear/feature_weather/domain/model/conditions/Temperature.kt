package com.puntogris.smartwear.feature_weather.domain.model.conditions

import com.puntogris.smartwear.core.utils.constants.Constants

sealed class Temperature(value: Int) : WeatherCondition(value) {

    class Celsius(value: Int) : Temperature(value) {
        override fun asString(): String = "$value °C"
        override fun metricValue(): Int = this.value
    }

    class Fahrenheit(value: Int) : Temperature(value) {
        override fun asString(): String = "$value °F"
        override fun metricValue(): Int = (value - 32) * 5 / 9
    }

    class Kelvin(value: Int) : Temperature(value) {
        override fun asString(): String = "$value °K"
        override fun metricValue(): Int = (value - 273.1).toInt()
    }

    companion object {
        fun from(value: Float, unit: String): Temperature {
            return when (unit) {
                Constants.METRIC -> Celsius(value.toInt())
                Constants.IMPERIAL -> Fahrenheit(value.toInt())
                else -> Kelvin(value.toInt())
            }
        }
    }
}

