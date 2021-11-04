package com.puntogris.smartwear.feature_weather.domain.model.conditions

open class WeatherCondition(val value: Int) {
    open fun asString(): String = "$value %"
    open fun metricValue(): Int = value
}
