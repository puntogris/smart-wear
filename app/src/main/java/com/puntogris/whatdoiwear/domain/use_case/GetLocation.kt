package com.puntogris.whatdoiwear.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.puntogris.whatdoiwear.data.data_source.toDomain
import com.puntogris.whatdoiwear.data.repository.LocationRepositoryImpl
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocation @Inject constructor(
    private val repository : LocationRepository
){
    operator fun invoke(): LiveData<Location?>{
        return repository.getLocalLastLocation().map {
            it?.toDomain()
        }
    }
}