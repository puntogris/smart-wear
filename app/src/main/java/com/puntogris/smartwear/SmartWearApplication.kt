package com.puntogris.smartwear

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.puntogris.smartwear.core.utils.ThemeManager
import com.puntogris.smartwear.feature_weather.data.data_source.local.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SmartWearApplication : Application() {

    @Inject
    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        setupTheme()
        AndroidThreeTen.init(this)
    }

    private fun setupTheme() {
        val theme = sharedPreferences.appTheme()
        themeManager.applyTheme(theme)
    }
}