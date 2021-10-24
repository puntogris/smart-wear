package com.puntogris.whatdoiwear.utils

import androidx.appcompat.app.AppCompatDelegate

object ThemeUtils {

    const val LIGHT = "light"
    const val DARK = "dark"

    fun applyTheme(theme: String){
        when(theme){
            LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}

