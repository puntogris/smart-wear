package com.puntogris.whatdoiwear.domain.use_case

import javax.inject.Inject

class LocationUseCases @Inject constructor(
    val insertLocation: InsertLocation,
    val getLocation: GetLocation,
    val updateLocation: UpdateLocation,
    val getGeocodingLocations: GetGeocodingLocations
)