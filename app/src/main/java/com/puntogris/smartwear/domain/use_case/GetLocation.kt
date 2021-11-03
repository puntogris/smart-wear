package com.puntogris.smartwear.domain.use_case

import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocation @Inject constructor(
    private val repository : LocationRepository
){
    operator fun invoke(): Flow<Location?> {
        return repository.getLocalLastLocation()
    }
}