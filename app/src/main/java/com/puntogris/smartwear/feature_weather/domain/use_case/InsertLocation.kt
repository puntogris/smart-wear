package com.puntogris.smartwear.feature_weather.domain.use_case

import com.puntogris.smartwear.feature_weather.domain.model.Location
import com.puntogris.smartwear.feature_weather.domain.repository.LocationRepository
import javax.inject.Inject

class InsertLocation @Inject constructor(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(location: Location) {
        repository.insertLastLocation(location)
    }
}