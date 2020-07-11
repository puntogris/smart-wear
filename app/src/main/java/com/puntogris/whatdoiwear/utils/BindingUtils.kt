package com.puntogris.whatdoiwear.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import kotlin.math.roundToInt

@BindingAdapter("doubleToStringPercentage")
fun TextView.setDoubleToStringPercentage(double: Double) {
    val doubleText = (double * 100).toInt()
    text = context.getString(R.string.percentage_symbol, doubleText)
}

@BindingAdapter("getDataWithoutNull")
fun TextView.setDataWithoutNull(data: String) {
    text = if (data == "null") ""
    else data
}

@BindingAdapter("windSpeedToKmH")
fun TextView.setWindSpeedToKmH(windSpeed: Double) {
    val windSpeedText = (windSpeed / 0.277778).roundToInt()
    text = context.getString(R.string.kmh_symbol, windSpeedText)
}

@BindingAdapter("weatherTemperature")
fun TextView.setWeatherTemperature(temperature: Double) {
    text = context.getString(R.string.temperature_celsius, temperature.roundToInt())
}

@BindingAdapter("backgroundImage")
fun ConstraintLayout.setBackgroundImage(time: String){
    val background =
        if (time.takeLast(2) == Constants.PM_TIME) R.drawable.ic_night_background
        else R.drawable.ic_day_background
    setBackgroundResource(background)
}

@BindingAdapter("weatherImage")
fun ImageView.setWeatherImages(weatherStatus: String) {

    var imgResId: Int = R.drawable.ic_015_moon

    setLayerType(View.LAYER_TYPE_SOFTWARE, null)

    when (weatherStatus) {
        "clear-day" -> imgResId = R.drawable.ic_007_sunny_1
        "clear-night" -> imgResId = R.drawable.ic_015_moon
        "rain" -> imgResId = R.drawable.ic_012_rain_1
        "snow" -> imgResId = R.drawable.ic_010_snows
        "sleet" -> imgResId = R.drawable.ic_017_snowing
        "wind" -> imgResId = R.drawable.ic_001_wind_1
        "fog" -> imgResId = R.drawable.ic_016_hazy
        "cloudy" -> imgResId = R.drawable.ic_018_cloudy_1
        "partly-cloudy-day" -> imgResId = R.drawable.ic_013_sunny
        "partly-cloudy-night" -> imgResId = R.drawable.ic_020_cloudy
    }
    setImageResource(imgResId)

}

@BindingAdapter("weatherRecommendation")
fun TextView.buildClothingRecommendation(body: WeatherBodyApi?) {
    if (body == null) return

    val currentTemp = body.currently.temperature.toInt()
    val humidity = (body.currently.humidity * 100).roundToInt()
    val currentWind = (body.currently.windSpeed.toInt() / 0.277778)
    val currentPrecip = (body.currently.precipProbability *100).roundToInt()
    val tempIn3Hours = body.hourly.data[3].temperature.toInt()
    val tempIn6Hours = body.hourly.data[6].temperature.toInt()
    val precipIn3Hours = (body.hourly.data[3].precipProbability * 100).roundToInt()
    val precipIn6Hours = (body.hourly.data[6].precipProbability * 100).roundToInt()

    var recommendation = when (currentTemp) {
        in Int.MIN_VALUE..0 -> "*Peligro! Llego la era glaciar, ponete todo la ropa que tengas!\n\n"
        in 1..9 -> "*Hace mucho frio! Buzo y/o campera si o si! No te olvides la bufanda y guantes.\n\n"
        in 10..14 -> "*No subestimes el frio que hace, por mas que adentro este para un buzo o pullover! Sali bien abrigado/a.\n\n"
        in 15..19 -> "*Con un buzo o campera ligera vas a estar bien.\n\n"
        in 20..29 -> "*Temperatura perfecta! Una remera, jeans y estas listo!\n\n"
        in 30..34 -> "*Momento de ponerse un short, remera o camisa livianita y invitate solo a la casa de un amigo/a con pileta.\n\n"
        in 35..39 -> "*Tirate a la pileta...sacate la ropa primero... estas ahi?\n\n"
        !in 0..40 -> "*Alerta roja!! Un paso afuera garantiza que seas rostizado.\n\n"
        else -> "Error analizando la temperatura"
    }

    recommendation += if (tempIn3Hours in (currentTemp + 1) until tempIn6Hours && (tempIn6Hours - currentTemp) >= 5) {
        "*Va a aumentar bastante la temperatura.\n\n"
    } else if (tempIn3Hours in (tempIn6Hours + 1) until currentTemp && (currentTemp - tempIn6Hours) >= 5) {
        "*Va a disminuir bastante la temperatura.\n\n"
    } else if (currentTemp < tempIn3Hours && (tempIn3Hours - currentTemp) >= 3) {
        "*Va a aumentar un poco la temperatura.\n\n"
    } else if (currentTemp > tempIn3Hours && (currentTemp - tempIn3Hours) >= 3) {
        "*Va a disminuir un poco la temperatura.\n\n"
    } else {
        "*La temperatura no va a cambiar por unas horas.\n\n"
    }

    recommendation += when {
        currentPrecip > 15 -> "*Esta lloviznando un poco.\n\n"
        currentPrecip > 60 -> "*Sali con un paraguas o una campera impermeable.\n\n"
        else -> ""
    }

    recommendation += if (precipIn3Hours in (currentPrecip + 1) until precipIn6Hours && (precipIn6Hours - currentPrecip) >= 50) {
        "*Se viene con toda la lluvia mas tarde!\n\n"
    } else if (precipIn3Hours in (precipIn6Hours + 1) until currentPrecip && (currentPrecip - precipIn6Hours) >= 50) {
        "*La lluvia va a frenar mas tarde!\n\n"
    } else if (currentPrecip > precipIn3Hours && (currentPrecip - precipIn3Hours) >= 25) {
        "*Atenti, puede que pare la lluvia mas tarde.\n\n"
    } else if (currentPrecip < precipIn3Hours && precipIn3Hours - currentPrecip >= 25) {
        "*Atenti, puede que llueva mas tarde.\n\n"
    } else ""


    recommendation += if (currentWind > 17) {
        if ((0..1).random() == 1) {
            "*Peinarse?.. Que es eso? Hay mucho viento.\n\n"
        } else {
            "*Que viento! Si salis no te olvides de cerrar las ventanas.\n\n"
        }
    } else {
        "*Muy poco viento, perfecto para estrenar tu nueva peluca.\n\n"
    }

    if (humidity > 89) {
        recommendation += "*Hay mucha humedad, juck.\n\n"
    }

    text = recommendation

}

