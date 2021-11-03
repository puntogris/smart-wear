package com.puntogris.smartwear.data.data_source

import com.puntogris.smartwear.data.data_source.local.model.LocationEntity
import com.puntogris.smartwear.data.data_source.remote.dto.LocationDto
import com.puntogris.smartwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.smartwear.domain.model.*
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

fun WeatherDto.toDomain(units: String): WeatherResult {
    return WeatherResult(
        Current(
            temperature = Temperature.from(current.temperature, units),
            description = current.weather.first().description,
            icon = current.weather.first().icon
        ),
        daily.map {
            Daily(
                min = Temperature.from(it.temperature.min, units),
                max = Temperature.from(it.temperature.max, units),
            )
        },
        hourly.map {
            Hourly(
                temperature = Temperature.from(it.temp, units),
                humidity = Humidity(it.humidity),
                windSpeed = Wind.from(it.windSpeed, units),
                precipitation = Precipitation(it.precipitation.toInt())
            )
        }
    )
}
