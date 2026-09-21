package com.puntogris.smartwear.data.data_source

import com.puntogris.smartwear.data.data_source.local.model.LocationEntity
import com.puntogris.smartwear.data.data_source.remote.dto.LocationDto
import com.puntogris.smartwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.smartwear.domain.model.Current
import com.puntogris.smartwear.domain.model.Daily
import com.puntogris.smartwear.domain.model.Hourly
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.model.WeatherResult
import com.puntogris.smartwear.domain.model.conditions.Humidity
import com.puntogris.smartwear.domain.model.conditions.Precipitation
import com.puntogris.smartwear.domain.model.conditions.Temperature
import com.puntogris.smartwear.domain.model.conditions.Wind

fun LocationEntity.toDomain(): Location {
    return Location(
        name = name,
        latitude = latitude,
        longitude = longitude
    )
}

fun LocationDto.toDomain(): Location {
    return Location(
        displayName = displayName,
        name = address.name,
        latitude = latitude.toDouble(),
        longitude = longitude.toDouble()
    )
}

fun LocationDto.toEntity(): LocationEntity {
    return LocationEntity(
        name = address.name,
        latitude = latitude.toDouble(),
        longitude = longitude.toDouble()
    )
}

fun Location.toEntity(): LocationEntity {
    return LocationEntity(
        name = name,
        latitude = latitude,
        longitude = longitude
    )
}

fun WeatherDto.toDomain(units: String, language: String): WeatherResult {
    val condition = WmoWeatherCondition.from(
        code = current.weatherCode,
        isDay = current.isDay == 1,
        language = language
    )
    val hourlyCount = minOf(
        hourly.temperatures.size,
        hourly.humidity.size,
        hourly.windSpeeds.size,
        hourly.precipitationProbabilities.size
    )
    val dailyCount = minOf(
        daily.minimumTemperatures.size,
        daily.maximumTemperatures.size
    )

    return WeatherResult(
        Current(
            temperature = Temperature.from(current.temperature.forUnits(units), units),
            description = condition.description,
            icon = condition.icon
        ),
        (0 until dailyCount).map { index ->
            Daily(
                min = Temperature.from(daily.minimumTemperatures[index].forUnits(units), units),
                max = Temperature.from(daily.maximumTemperatures[index].forUnits(units), units),
            )
        },
        (0 until hourlyCount).map { index ->
            Hourly(
                temperature = Temperature.from(hourly.temperatures[index].forUnits(units), units),
                humidity = Humidity(hourly.humidity[index]),
                windSpeed = Wind.from(hourly.windSpeeds[index], units),
                precipitation = Precipitation(hourly.precipitationProbabilities[index])
            )
        }
    )
}

private fun Float.forUnits(units: String): Float {
    return if (units == "standard") this + 273.15f else this
}
