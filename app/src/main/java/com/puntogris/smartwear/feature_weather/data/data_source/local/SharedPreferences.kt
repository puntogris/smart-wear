package com.puntogris.smartwear.feature_weather.data.data_source.local

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.puntogris.smartwear.BuildConfig
import com.puntogris.smartwear.core.utils.ThemeManager
import com.puntogris.smartwear.core.utils.constants.Keys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun appTheme() = sharedPreferences.getString(Keys.THEME, ThemeManager.LIGHT)

    fun enableShowAnimationPref() = sharedPreferences.enableShowAnimationPref()

    fun isAnimationEnabled() = sharedPreferences.getBoolean(Keys.ANIMATION, false)

    fun lastVersionCode() = sharedPreferences.getInt(Keys.APP_VERSION, 0)

    fun updateLastVersionCode() =
        sharedPreferences.edit().putInt(Keys.APP_VERSION, BuildConfig.VERSION_CODE).apply()

    fun weatherUnits() = sharedPreferences.getString(Keys.WEATHER_UNITS, "metric")

    fun showWelcome() = sharedPreferences.getBoolean(Keys.SHOW_WELCOME, true)

    fun disableWelcomeScreenPref() =
        sharedPreferences.edit().putBoolean(Keys.SHOW_WELCOME, false).apply()
}

fun SharedPreferences.enableShowAnimationPref() =
    this.edit().putBoolean(Keys.ANIMATION, true).apply()
