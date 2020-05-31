package com.puntogris.whatdoiwear.utils

import android.content.Context
import android.content.SharedPreferences
import com.puntogris.whatdoiwear.R
import javax.inject.Inject

class MySharedPreferences @Inject constructor(private val sharedPreferences: SharedPreferences, private val context: Context) {
    private val defaultValue = context.getString(R.string.player_name_shared_pref_default_value)
    private val key = Constants.SP_NAME_KEY

    fun putData(data: String) {
        val userName =
            if (data.isEmpty()) defaultValue
            else context.getString(R.string.player_name_shared_pref, data)
        sharedPreferences.edit().putString(key, userName).apply()
    }
    fun getData() = sharedPreferences.getString(key, defaultValue)

    fun exists(): Boolean = sharedPreferences.contains(key)

    fun delete() =  sharedPreferences.edit().remove(key).apply()

}