package com.puntogris.whatdoiwear.common.constants

object HttpRoutes {
    private const val OPEN_WEATHER_API_BASE_URL = "https://api.openweathermap.org"
    private const val OPEN_WEATHER_BASE_URL = "https://openweathermap.org"

    const val WEATHER = "$OPEN_WEATHER_API_BASE_URL/data/2.5/onecall"
    const val WEATHER_ICON = "$OPEN_WEATHER_BASE_URL/img/wn"

    private const val LOCATIONIQ_BASE_URL = "https://us1.locationiq.com/"
    const val FORWARD_GEOCODING = "$LOCATIONIQ_BASE_URL/v1/search.php"
    const val REVERSE_GEOCODING = "$LOCATIONIQ_BASE_URL/v1/reverse.php"
}