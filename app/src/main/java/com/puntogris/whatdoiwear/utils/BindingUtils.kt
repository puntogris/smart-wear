package com.puntogris.whatdoiwear.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import kotlin.math.roundToInt

@BindingAdapter("weatherImage")
fun ImageView.setWeatherImages(weatherStatus:String){

    var imgResId: Int = R.drawable.ic_015_moon

    this.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

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
    this.setImageResource(imgResId)

}

@BindingAdapter("weatherRecommendation")
fun TextView.buildClothingRecommendation(weatherBodyApi: WeatherBodyApi?) {
    if(weatherBodyApi != null) {
        val tempAhora: Int = weatherBodyApi.currently.temperature.toInt()
        val humedadAhora: Double = weatherBodyApi.currently.humidity
        val vientoAhora: Int = weatherBodyApi.currently.windSpeed.toInt()
        val lluviaAhora: Double = weatherBodyApi.currently.precipProbability
        val temp3horas: Int = weatherBodyApi.hourly.data[3].temperature.toInt()
        val temp6horas: Int = weatherBodyApi.hourly.data[6].temperature.toInt()
        val lluvia3horas: Int = weatherBodyApi.hourly.data[3].precipProbability.toInt()
        val lluvia6horas: Int = weatherBodyApi.hourly.data[6].precipProbability.toInt()
        var finalRecommendationText = ""

        when {
            tempAhora <= 0 -> finalRecommendationText += "*Peligro! Llego la era glaciar, ponete todo la ropa que tengas!\n\n"
            tempAhora in 1..9 -> finalRecommendationText += "*Hace mucho frio! Buzo y/o campera si o si! No te olvides la bufanda y guantes.\n\n"
            tempAhora in 10..14 -> finalRecommendationText += "*No subestimes el frio que hace, por mas que adentro este para un buzo o pullover! Sali bien abrigado/a.\n\n"
            tempAhora in 15..19 -> finalRecommendationText += "*Con un buzo o campera ligera vas a estar bien.\n\n"
            tempAhora in 20..29 -> finalRecommendationText += "*Temperatura perfecta! Una remera, jeans y estas listo!\n\n"
            tempAhora in 30..34 -> // si ves gente que se esta sacando to-do no es por que esten jugando al strip poker
                finalRecommendationText += "*Momento de ponerse un short, remera o camisa livianita y invitate solo a la casa de un amigo/a con pileta.\n\n"
            tempAhora in 35..39 -> finalRecommendationText += "*Tirate a la pileta...sacate la ropa primero... estas ahi?\n\n"
            tempAhora >= 40 -> finalRecommendationText += "*Alerta roja!! Un paso afuera garantiza que seas rostizado.\n\n"
        }

        finalRecommendationText += if (temp3horas in (tempAhora + 1) until temp6horas && (temp6horas - tempAhora) >= 5) {
            "*Va a aumentar bastante la temperatura.\n\n"
        } else if (temp3horas in (temp6horas + 1) until tempAhora && (tempAhora - temp6horas) >= 5) {
            "*Va a disminuir bastante la temperatura.\n\n"
        } else if (tempAhora < temp3horas && (temp3horas - tempAhora) >= 3) {
            "*Va a aumentar un poco la temperatura.\n\n"
        } else if (tempAhora > temp3horas && (tempAhora - temp3horas) >= 3) {
            "*Va a disminuir un poco la temperatura.\n\n"
        } else {
            "*La temperatura no va a cambiar por unas horas.\n\n"
        }

        val lluviaAhora2 = (lluviaAhora * 100).roundToInt()
        if (lluviaAhora2 > 15) {
            finalRecommendationText += "*Esta lloviznando un poco.\n\n"
        } else if (lluviaAhora2 > 60)
            finalRecommendationText += "*Sali con un paraguas o una campera impermeable.\n\n"

        if (lluvia3horas in (lluviaAhora2 + 1) until lluvia6horas && (lluvia6horas - lluviaAhora) >= 50) {
            finalRecommendationText += "*Se viene con toda la lluvia mas tarde!\n\n"
        } else if (lluvia3horas in (lluvia6horas + 1) until lluviaAhora2 && (lluviaAhora - lluvia6horas) >= 50) {
            finalRecommendationText += "*La lluvia va a frenar mas tarde!\n\n"
        } else if (lluviaAhora2 > lluvia3horas && (lluviaAhora - lluvia3horas) >= 25) {
            finalRecommendationText += "*Atenti, puede que pare la lluvia mas tarde.\n\n"
        } else if (lluviaAhora2 < lluvia3horas && lluvia3horas - lluviaAhora >= 25) {
            finalRecommendationText += "*Atenti, puede que llueva mas tarde.\n\n"
        }

        val decidir = (0..1).random()
        val viento: String

        if ((vientoAhora / 0.277778) > 17) {
            viento = if (decidir == 1) {
                "*Peinarse?.. Que es eso? Hay mucho viento.\n\n"
            } else {
                "*Que viento! Si salis no te olvides de cerrar las ventanas.\n\n"

            }
            finalRecommendationText += viento
        } else {
            finalRecommendationText += "*Muy poco viento, perfecto para estrenar tu nueva peluca.\n\n"
        }

        if ((humedadAhora * 100).roundToInt() > 89) {
            finalRecommendationText += "*Hay mucha humedad, juck.\n\n"
        }

        this.text = finalRecommendationText

    }else{
        this.text = ""
    }
}

