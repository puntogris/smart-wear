package com.puntogris.whatdoiwear.utils

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun enableShowAnimationPref() = sharedPreferences.edit().putBoolean(PreferencesKeys.ANIMATION, true).apply()

    fun getShowAnimationPref() = sharedPreferences.getBoolean(PreferencesKeys.ANIMATION, false)

}