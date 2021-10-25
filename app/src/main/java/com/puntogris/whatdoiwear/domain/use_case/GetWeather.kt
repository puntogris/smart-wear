package com.puntogris.whatdoiwear.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.puntogris.whatdoiwear.data.repository.WeatherRepositoryImpl
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.common.WeatherResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeather @Inject constructor(
    private val repository: WeatherRepositoryImpl
){

    operator fun invoke(location: Location?): LiveData<WeatherResult> = liveData {
        try {
            emit(WeatherResult.InProgress)
            val weather = repository.getWeather(location!!)
            emit(WeatherResult.Success(weather))
        }catch (e:Exception){
            emit(WeatherResult.Error)
        }
    }
}