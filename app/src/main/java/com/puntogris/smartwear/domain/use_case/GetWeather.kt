package com.puntogris.smartwear.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.puntogris.smartwear.common.WeatherResult
import com.puntogris.smartwear.data.data_source.toDomain
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeather @Inject constructor(
    private val repository: WeatherRepository
){
    operator fun invoke(location: Location?): LiveData<WeatherResult> = liveData {
        try {
            emit(WeatherResult.Loading)
            val weather = repository.getWeather(location!!).toDomain()
            emit(WeatherResult.Success(weather))
        }catch (e:Exception){
            emit(WeatherResult.Error)
        }
    }
}