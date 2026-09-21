package com.puntogris.smartwear.feature_weather.data.data_source

import com.google.gson.Gson
import com.puntogris.smartwear.feature_weather.data.data_source.remote.dto.WeatherDto
import org.junit.Assert.assertEquals
import org.junit.Test

class OpenMeteoMapperTest {

    private val response = Gson().fromJson(
        """
        {
          "current": {
            "temperature_2m": 21.5,
            "weather_code": 2,
            "is_day": 1
          },
          "hourly": {
            "temperature_2m": [21.5, 20.0],
            "relative_humidity_2m": [70, 75],
            "precipitation_probability": [10, 40],
            "wind_speed_10m": [3.5, 4.0]
          },
          "daily": {
            "temperature_2m_min": [14.0],
            "temperature_2m_max": [24.0]
          }
        }
        """.trimIndent(),
        WeatherDto::class.java
    )

    @Test
    fun `maps Open-Meteo response to metric weather`() {
        val weather = response.toDomain(units = "metric", language = "en")

        assertEquals(21, weather.current.temperature.value)
        assertEquals("Partly cloudy", weather.current.description)
        assertEquals("03d", weather.current.icon)
        assertEquals(14, weather.daily.first().min.value)
        assertEquals(24, weather.daily.first().max.value)
        assertEquals(40, weather.hourly[1].precipitation.value)
        assertEquals(4, weather.hourly[1].windSpeed.value)
    }

    @Test
    fun `maps WMO description to Spanish`() {
        val weather = response.toDomain(units = "metric", language = "es")

        assertEquals("Parcialmente nublado", weather.current.description)
    }

    @Test
    fun `converts Celsius response to standard Kelvin units`() {
        val weather = response.toDomain(units = "standard", language = "en")

        assertEquals(294, weather.current.temperature.value)
        assertEquals(287, weather.daily.first().min.value)
    }
}
