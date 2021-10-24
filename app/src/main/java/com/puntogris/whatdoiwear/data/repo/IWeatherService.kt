package com.puntogris.whatdoiwear.data.repo

import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.WeatherResponse

interface IWeatherService {
    suspend fun getWeather(lastLocation: LastLocation): WeatherResponse
}