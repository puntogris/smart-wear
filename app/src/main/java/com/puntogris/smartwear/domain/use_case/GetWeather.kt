package com.puntogris.smartwear.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.puntogris.smartwear.common.WeatherResult
import com.puntogris.smartwear.domain.model.Hourly
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeather @Inject constructor(
    private val repository: WeatherRepository
){
    operator fun invoke(location: Location?): LiveData<WeatherResult> = liveData {
        try {
            if (location == null) return@liveData
            emit(WeatherResult.Loading)
            val weather = repository.getWeather(location)

            var precipitation = Pair<Hourly?, Int>(null, 0)
            var wind = Pair<Hourly?, Int>(null, 0)
            var humidity = Pair<Hourly?, Int>(null, 0)

            weather.hourly.subList(0, 8).forEachIndexed { i, h ->
                if (h.precipitation > precipitation.second) precipitation = Pair(h, i)
                if (h.windSpeed > wind.second) wind = Pair(h, i)
                if (h.humidity > humidity.second) humidity = Pair(h, i)
            }

            if (precipitation.first != null){

            }
            if (wind.first != null){

            }
            if (humidity.first != null){

            }


            emit(WeatherResult.Success(weather))
        }catch (e:Exception){
            emit(WeatherResult.Error)
        }
    }
}

class PrecipitationForecast(val value: Float, val time: EventTime)

enum class EventTime {
    Morning, Afternoon, Evening, Night
}