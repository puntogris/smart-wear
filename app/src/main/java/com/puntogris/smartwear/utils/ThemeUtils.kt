package com.puntogris.smartwear.utils

import androidx.appcompat.app.AppCompatDelegate
import com.puntogris.smartwear.data.data_source.local.SharedPref
import javax.inject.Inject

class ThemeUtils @Inject constructor(private val sharedPref: SharedPref){

    fun applyTheme(value: String? = null){
        when(value ?: sharedPref.appTheme()){
            LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    companion object {
        const val LIGHT = "light"
        const val DARK = "dark"
    }
}

