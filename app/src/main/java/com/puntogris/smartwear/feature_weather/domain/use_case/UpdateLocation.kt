package com.puntogris.smartwear.feature_weather.domain.use_case

import com.puntogris.smartwear.R
import com.puntogris.smartwear.feature_weather.domain.repository.LocationRepository
import com.puntogris.smartwear.feature_weather.presentation.util.LocationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateLocation @Inject constructor(
    private val repository: LocationRepository
) {
    operator fun invoke(): Flow<LocationResult> = flow {
        try {
            emit(LocationResult.Loading)
            repository.updateLastLocation()
            emit(LocationResult.Success.UpdateLocation)
        } catch (e: Exception) {
            emit(LocationResult.Error(R.string.snack_connection_error))
        }
    }

}