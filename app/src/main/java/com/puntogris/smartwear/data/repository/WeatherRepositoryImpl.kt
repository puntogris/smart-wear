package com.puntogris.smartwear.data.repository

import com.puntogris.smartwear.data.data_source.remote.WeatherApi
import com.puntogris.smartwear.data.data_source.toDomain
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.model.WeatherResult
import com.puntogris.smartwear.domain.repository.WeatherRepository
import com.puntogris.smartwear.utils.SharedPref
import java.util.*

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val sharedPref: SharedPref
) : WeatherRepository {

    override suspend fun getWeather(location: Location): WeatherResult {
        val units = sharedPref.weatherUnits()!!
        val language = getLanguageCode()
        val weather = weatherApi.getWeather(
            location = location,
            units = units,
            language = language
        )
        return weather.toDomain(units)
    }

    private fun getLanguageCode(): String {
        val localLanguage = Locale.getDefault().language
        val supportedLanguages = listOf("en", "es")
        return if (localLanguage !in supportedLanguages) "en" else localLanguage
    }
}
