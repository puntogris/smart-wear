package com.puntogris.whatdoiwear.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.utils.Constants.SP_ANIMATION_KEY
import com.puntogris.whatdoiwear.utils.Constants.SP_NAME_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun enableShowAnimationPref() = sharedPreferences.edit().putBoolean(SP_ANIMATION_KEY, true).apply()

    fun getShowAnimationPref() = sharedPreferences.getBoolean(SP_ANIMATION_KEY, false)

}