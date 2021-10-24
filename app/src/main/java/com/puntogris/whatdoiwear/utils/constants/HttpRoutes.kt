package com.puntogris.whatdoiwear.utils.constants

object HttpRoutes {
    private const val OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org"
    const val WEATHER = "$OPEN_WEATHER_BASE_URL/data/2.5/onecall"

    private const val LOCATIONIQ_BASE_URL = "https://us1.locationiq.com/"
    const val GEOCODING = "$LOCATIONIQ_BASE_URL/v1/search.php"
}