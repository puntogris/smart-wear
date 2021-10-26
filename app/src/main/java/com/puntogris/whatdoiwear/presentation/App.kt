package com.puntogris.whatdoiwear.presentation

import android.app.Application
import com.puntogris.whatdoiwear.utils.ThemeUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(){

    @Inject lateinit var themeUtils: ThemeUtils

    override fun onCreate() {
        super.onCreate()
        themeUtils.applyTheme()
    }
}