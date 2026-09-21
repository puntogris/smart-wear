package com.puntogris.smartwear.data.repository

import com.puntogris.smartwear.data.data_source.local.SharedPreferences
import com.puntogris.smartwear.data.data_source.remote.WeatherApi
import com.puntogris.smartwear.data.data_source.toDomain
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.model.WeatherResult
import com.puntogris.smartwear.domain.repository.WeatherRepository
import java.util.Locale

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val sharedPref: SharedPreferences
) : WeatherRepository {

    override suspend fun getWeather(location: Location): WeatherResult {
        val units = requireNotNull(sharedPref.weatherUnits())
        val language = getLanguageCode()
        val isImperial = units == "imperial"
        val weather = weatherApi.getWeather(
            lat = location.latitude,
            lon = location.longitude,
            temperatureUnit = if (isImperial) "fahrenheit" else "celsius",
            windSpeedUnit = if (isImperial) "mph" else "ms"
        )
        return weather.toDomain(units, language)
    }

    private fun getLanguageCode(): String {
        val localLanguage = Locale.getDefault().language
        val supportedLanguages = listOf("en", "es")
        return if (localLanguage !in supportedLanguages) "en" else localLanguage
    }
}
