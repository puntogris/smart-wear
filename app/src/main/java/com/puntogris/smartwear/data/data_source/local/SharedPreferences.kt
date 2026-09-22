package com.puntogris.smartwear.data.data_source.local

import android.content.Context
import android.content.SharedPreferences as AndroidSharedPreferences
import com.puntogris.smartwear.BuildConfig
import com.puntogris.smartwear.utils.ThemeManager
import com.puntogris.smartwear.utils.constants.Keys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        "${context.packageName}_preferences",
        Context.MODE_PRIVATE
    )

    fun appTheme() = sharedPreferences.getString(Keys.THEME, ThemeManager.LIGHT)

    fun enableShowAnimationPref() = sharedPreferences.enableShowAnimationPref()

    fun isAnimationEnabled() = sharedPreferences.getBoolean(Keys.ANIMATION, false)

    fun lastVersionCode() = sharedPreferences.getInt(Keys.APP_VERSION, 0)

    fun updateLastVersionCode() =
        sharedPreferences.edit().putInt(Keys.APP_VERSION, BuildConfig.VERSION_CODE).apply()

    fun weatherUnits() = sharedPreferences.getString(Keys.WEATHER_UNITS, "metric")

    fun updateTheme(value: String) =
        sharedPreferences.edit().putString(Keys.THEME, value).apply()

    fun updateWeatherUnits(value: String) =
        sharedPreferences.edit().putString(Keys.WEATHER_UNITS, value).apply()

    fun showWelcome() = sharedPreferences.getBoolean(Keys.SHOW_WELCOME, true)

    fun disableWelcomeScreenPref() =
        sharedPreferences.edit().putBoolean(Keys.SHOW_WELCOME, false).apply()
}

fun AndroidSharedPreferences.enableShowAnimationPref() =
    this.edit().putBoolean(Keys.ANIMATION, true).apply()
