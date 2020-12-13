package com.puntogris.whatdoiwear.utils

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.preference.PreferenceManager
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.utils.Constants.SP_ANIMATION_KEY
import com.puntogris.whatdoiwear.utils.Constants.SP_LAST_LOCATION
import com.puntogris.whatdoiwear.utils.Constants.SP_NAME_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MySharedPreferences @Inject constructor(@ApplicationContext private val context: Context) {
    private val defaultValue = context.getString(R.string.player_name_shared_pref_default_value)
    private val key = SP_NAME_KEY
    private val animationKey = SP_ANIMATION_KEY
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun putData(data: String) {
        val userName =
            if (data.isEmpty()) defaultValue
            else context.getString(R.string.player_name_shared_pref, data)
        sharedPreferences.edit().putString(key, userName).apply()
    }
    fun getData() = sharedPreferences.getString(key, defaultValue)

    fun exists(): Boolean = sharedPreferences.contains(key)

    fun delete() =  sharedPreferences.edit().remove(key).apply()

    fun setShowAnimationPref() = sharedPreferences.edit().putBoolean(SP_ANIMATION_KEY, true).apply()

    fun getShowAnimationPref() = sharedPreferences.getBoolean(SP_ANIMATION_KEY, false)

    fun getLastLocation() = sharedPreferences.getString(SP_LAST_LOCATION, "")

    fun setLastLocation(lastLocation: String) = sharedPreferences.edit().putString(SP_LAST_LOCATION, lastLocation).apply()

}