package com.puntogris.whatdoiwear.domain.use_case

import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.common.LocationResult
import com.puntogris.whatdoiwear.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateLocation @Inject constructor(
    private val repository: LocationRepository
){
    operator fun invoke(): Flow<LocationResult> = flow {
        try {
            emit(LocationResult.Loading)
            repository.updateLastLocation()
            emit(LocationResult.Success.UpdateLocation)
        }catch (e:Exception){
            emit(LocationResult.Error(R.string.snack_connection_error))
        }
    }
}