package com.puntogris.whatdoiwear.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.utils.Constants.SP_ANIMATION_KEY
import com.puntogris.whatdoiwear.utils.Constants.SP_NAME_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MySharedPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    private val defaultValue = context.getString(R.string.player_name_shared_pref_default_value)
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setUsernamePref(data: String) {
        val userName =
            if (data.isEmpty()) defaultValue
            else context.getString(R.string.player_name_shared_pref, data)
        sharedPreferences.edit().putString(SP_NAME_KEY, userName).apply()
    }

    fun getUsernamePref() = sharedPreferences.getString(SP_NAME_KEY, defaultValue)

    fun setShowAnimationPref() = sharedPreferences.edit().putBoolean(SP_ANIMATION_KEY, true).apply()

    fun getShowAnimationPref() = sharedPreferences.getBoolean(SP_ANIMATION_KEY, false)

}