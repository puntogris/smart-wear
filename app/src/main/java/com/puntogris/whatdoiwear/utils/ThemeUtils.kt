package com.puntogris.whatdoiwear.utils

import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject

class ThemeUtils @Inject constructor(private val sharedPref: SharedPref){

    fun applyTheme(){
        when(sharedPref.appTheme()){
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

