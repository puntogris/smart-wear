package com.puntogris.smartwear.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.puntogris.smartwear.data.data_source.toDomain
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.repository.LocationRepository
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