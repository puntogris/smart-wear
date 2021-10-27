package com.puntogris.smartwear.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.puntogris.smartwear.BuildConfig
import com.puntogris.smartwear.common.constants.PreferencesKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun appTheme() = sharedPreferences.getString(PreferencesKeys.THEME, ThemeUtils.LIGHT)

    fun enableShowAnimationPref() = sharedPreferences.edit().putBoolean(PreferencesKeys.ANIMATION, true).apply()

    fun isAnimationEnabled() = sharedPreferences.getBoolean(PreferencesKeys.ANIMATION, false)

    fun lastVersionCode() = sharedPreferences.getInt(PreferencesKeys.APP_VERSION, 0)

    fun updateLastVersionCode() = sharedPreferences.edit().putInt(PreferencesKeys.APP_VERSION, BuildConfig.VERSION_CODE).apply()

    fun weatherUnits() = sharedPreferences.getString(PreferencesKeys.WEATHER_UNITS, "metric")
}