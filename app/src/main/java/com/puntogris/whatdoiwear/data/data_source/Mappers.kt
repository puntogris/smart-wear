package com.puntogris.whatdoiwear.data.data_source

import com.puntogris.whatdoiwear.data.data_source.local.model.LocationEntity
import com.puntogris.whatdoiwear.data.data_source.remote.dto.LocationDto
import com.puntogris.whatdoiwear.domain.model.Location

fun android.location.Location.toDomain(): Location{
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
        name = name,
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