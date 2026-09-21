package com.puntogris.smartwear.data.data_source

data class WmoWeatherCondition(
    val description: String,
    val icon: String
) {
    companion object {
        fun from(code: Int, isDay: Boolean, language: String): WmoWeatherCondition {
            val descriptions = when (code) {
                0 -> "Clear sky" to "Cielo despejado"
                1 -> "Mainly clear" to "Mayormente despejado"
                2 -> "Partly cloudy" to "Parcialmente nublado"
                3 -> "Overcast" to "Nublado"
                45, 48 -> "Fog" to "Niebla"
                51, 53, 55 -> "Drizzle" to "Llovizna"
                56, 57 -> "Freezing drizzle" to "Llovizna helada"
                61 -> "Light rain" to "Lluvia ligera"
                63 -> "Moderate rain" to "Lluvia moderada"
                65 -> "Heavy rain" to "Lluvia intensa"
                66, 67 -> "Freezing rain" to "Lluvia helada"
                71 -> "Light snowfall" to "Nevada ligera"
                73 -> "Moderate snowfall" to "Nevada moderada"
                75, 77 -> "Heavy snowfall" to "Nevada intensa"
                80 -> "Light rain showers" to "Chubascos ligeros"
                81 -> "Moderate rain showers" to "Chubascos moderados"
                82 -> "Heavy rain showers" to "Chubascos intensos"
                85, 86 -> "Snow showers" to "Chubascos de nieve"
                95 -> "Thunderstorm" to "Tormenta eléctrica"
                96, 99 -> "Thunderstorm with hail" to "Tormenta con granizo"
                else -> "Unknown conditions" to "Condiciones desconocidas"
            }
            val iconBase = when (code) {
                0 -> "01"
                1 -> "02"
                2 -> "03"
                3 -> "04"
                45, 48 -> "50"
                51, 53, 55, 56, 57, 80, 81, 82 -> "09"
                61, 63, 65 -> "10"
                66, 67, 71, 73, 75, 77, 85, 86 -> "13"
                95, 96, 99 -> "11"
                else -> "03"
            }

            return WmoWeatherCondition(
                description = if (language == "es") descriptions.second else descriptions.first,
                icon = iconBase + if (isDay) "d" else "n"
            )
        }
    }
}
