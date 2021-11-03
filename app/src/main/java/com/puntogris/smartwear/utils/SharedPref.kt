package com.puntogris.smartwear.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.puntogris.smartwear.BuildConfig
import com.puntogris.smartwear.common.constants.Keys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun appTheme() = sharedPreferences.getString(Keys.THEME, ThemeUtils.LIGHT)

    fun enableShowAnimationPref() = sharedPreferences.edit().putBoolean(Keys.ANIMATION, true).apply()

    fun isAnimationEnabled() = sharedPreferences.getBoolean(Keys.ANIMATION, false)

    fun lastVersionCode() = sharedPreferences.getInt(Keys.APP_VERSION, 0)

    fun updateLastVersionCode() = sharedPreferences.edit().putInt(Keys.APP_VERSION, BuildConfig.VERSION_CODE).apply()

    fun weatherUnits() = sharedPreferences.getString(Keys.WEATHER_UNITS, "metric")

    fun showWelcome() = sharedPreferences.getBoolean(Keys.SHOW_WELCOME, true)

    fun disableWelcomeScreenPref() = sharedPreferences.edit().putBoolean(Keys.SHOW_WELCOME, false).apply()
}