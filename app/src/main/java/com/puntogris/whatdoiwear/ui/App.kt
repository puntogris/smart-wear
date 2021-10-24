package com.puntogris.whatdoiwear.ui

import android.app.Application
import com.puntogris.whatdoiwear.utils.SharedPref
import com.puntogris.whatdoiwear.utils.ThemeUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App :Application(){

    @Inject lateinit var sharedPref: SharedPref

    override fun onCreate() {
        super.onCreate()

        ThemeUtils.applyTheme(sharedPref.appTheme())
    }
}