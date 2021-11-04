package com.puntogris.smartwear.feature_weather.domain.use_case

import javax.inject.Inject

class LocationUseCases @Inject constructor(
    val insertLocation: InsertLocation,
    val getLocation: GetLocation,
    val updateLocation: UpdateLocation,
    val getGeocodingLocations: GetGeocodingLocations
)