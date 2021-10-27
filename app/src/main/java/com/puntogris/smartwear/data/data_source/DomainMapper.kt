package com.puntogris.smartwear.data.data_source

import com.puntogris.smartwear.data.data_source.local.model.LocationEntity
import com.puntogris.smartwear.data.data_source.remote.dto.LocationDto
import com.puntogris.smartwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.smartwear.domain.model.*

fun android.location.Location.toDomain(): Location {
    return Location(
        latitude = latitude,
        longitude = longitude
    )
}

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

fun WeatherDto.toDomain(): Weather{
    return Weather(
        Current(
            temperature = current.temperature,
            description = current.weather.first().description,
            icon = current.weather.first().icon
        ),
        daily.map {
            Daily(
                min = it.temperature.min,
                max = it.temperature.max
            )
        },
        hourly.map {
            Hourly(
                temperature = it.temp,
                humidity = it.humidity,
                windSpeed = it.windSpeed
            )
        }
    )
}