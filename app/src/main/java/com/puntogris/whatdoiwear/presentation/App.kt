package com.puntogris.whatdoiwear.presentation

import android.app.Application
import com.puntogris.whatdoiwear.utils.SharedPref
import com.puntogris.whatdoiwear.utils.ThemeUtils
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidApp
class App : Application(){

    @Inject lateinit var sharedPref: SharedPref

    override fun onCreate() {
        super.onCreate()

        sharedPref.appTheme()?.let {
            ThemeUtils.applyTheme(it)
        }
    }
}