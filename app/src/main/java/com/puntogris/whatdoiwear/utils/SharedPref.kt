package com.puntogris.whatdoiwear.utils

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.preference.PreferenceManager
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.puntogris.whatdoiwear.utils.constants.PreferencesKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val flowSharedPreferences = FlowSharedPreferences(sharedPreferences)

    fun appTheme() = flowSharedPreferences.getString(PreferencesKeys.THEME, ThemeUtils.LIGHT).get()

    fun enableShowAnimationPref() = sharedPreferences.edit().putBoolean(PreferencesKeys.ANIMATION, true).apply()

    fun isAnimationEnabledLiveData() = flowSharedPreferences.getBoolean(PreferencesKeys.ANIMATION).asFlow().asLiveData()

}