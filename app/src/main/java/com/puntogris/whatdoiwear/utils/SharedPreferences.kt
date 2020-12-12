package com.puntogris.whatdoiwear.utils

import android.content.Context
import android.content.SharedPreferences
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.utils.Constants.SP_ANIMATION_KEY
import com.puntogris.whatdoiwear.utils.Constants.SP_NAME_KEY
import javax.inject.Inject

class MySharedPreferences @Inject constructor(private val sharedPreferences: SharedPreferences, private val context: Context) {
    private val defaultValue = context.getString(R.string.player_name_shared_pref_default_value)
    private val key = SP_NAME_KEY
    private val animationKey = SP_ANIMATION_KEY

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

}