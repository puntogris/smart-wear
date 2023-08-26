package com.puntogris.smartwear.feature_weather.data.repository

import com.puntogris.smartwear.feature_weather.data.data_source.local.SharedPreferences
import com.puntogris.smartwear.feature_weather.data.data_source.remote.WeatherApi
import com.puntogris.smartwear.feature_weather.data.data_source.toDomain
import com.puntogris.smartwear.feature_weather.domain.model.Location
import com.puntogris.smartwear.feature_weather.domain.model.WeatherResult
import com.puntogris.smartwear.feature_weather.domain.repository.WeatherRepository
import java.util.Locale

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val sharedPref: SharedPreferences
) : WeatherRepository {

    override suspend fun getWeather(location: Location): WeatherResult {
        val units = requireNotNull(sharedPref.weatherUnits())
        val language = getLanguageCode()
        val weather = weatherApi.getWeather(
            lat = location.latitude,
            lon = location.longitude,
            units = units,
            lang = language
        )
        return weather.toDomain(units)
    }

    private fun getLanguageCode(): String {
        val localLanguage = Locale.getDefault().language
        val supportedLanguages = listOf("en", "es")
        return if (localLanguage !in supportedLanguages) "en" else localLanguage
    }
}
