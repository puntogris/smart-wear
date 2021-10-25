package com.puntogris.whatdoiwear.domain.use_case

import com.puntogris.whatdoiwear.data.repository.LocationRepositoryImpl
import com.puntogris.whatdoiwear.common.SimpleResult
import com.puntogris.whatdoiwear.domain.repository.LocationRepository
import javax.inject.Inject

class UpdateLastLocation @Inject constructor(
    private val repository: LocationRepository
){

    suspend operator fun invoke(): SimpleResult {
        return repository.updateLastLocation()
    }
}