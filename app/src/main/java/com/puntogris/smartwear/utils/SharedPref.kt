package com.puntogris.smartwear.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.puntogris.smartwear.common.constants.PreferencesKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun appTheme() = sharedPreferences.getString(PreferencesKeys.THEME, ThemeUtils.LIGHT)

    fun enableShowAnimationPref() = sharedPreferences.edit().putBoolean(PreferencesKeys.ANIMATION, true).apply()

    fun isAnimationEnabled() = sharedPreferences.getBoolean(PreferencesKeys.ANIMATION, false)

}