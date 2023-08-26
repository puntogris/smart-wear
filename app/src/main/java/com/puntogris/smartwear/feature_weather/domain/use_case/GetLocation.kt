package com.puntogris.smartwear.feature_weather.domain.use_case

import com.puntogris.smartwear.feature_weather.domain.model.Location
import com.puntogris.smartwear.feature_weather.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocation @Inject constructor(
    private val repository: LocationRepository
) {
    operator fun invoke(): Flow<Location?> {
        return repository.getLocalLastLocation()
    }
}