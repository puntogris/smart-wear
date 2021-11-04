package com.puntogris.smartwear.core.utils

import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject

class ThemeManager @Inject constructor() {

    fun applyTheme(value: String?) {
        when (value ?: return) {
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

