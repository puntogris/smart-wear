package com.puntogris.whatdoiwear.domain.use_case

import com.puntogris.whatdoiwear.data.repository.LocationRepositoryImpl
import com.puntogris.whatdoiwear.utils.SimpleResult
import javax.inject.Inject

class UpdateLastLocation @Inject constructor(
    private val repository: LocationRepositoryImpl
){

    suspend operator fun invoke(): SimpleResult{
        return repository.updateLastLocation()
    }
}