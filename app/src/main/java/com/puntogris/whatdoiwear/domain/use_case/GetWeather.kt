package com.puntogris.whatdoiwear.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.puntogris.whatdoiwear.common.WeatherResult
import com.puntogris.whatdoiwear.data.data_source.toDomain
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.domain.repository.WeatherRepository
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