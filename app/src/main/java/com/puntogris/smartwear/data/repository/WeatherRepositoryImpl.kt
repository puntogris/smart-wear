package com.puntogris.smartwear.data.repository

import com.puntogris.smartwear.data.data_source.remote.WeatherApi
import com.puntogris.smartwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
): WeatherRepository{

    override suspend fun getWeather(location: Location): WeatherDto {
         return weatherApi.getWeather(location)
    }
}