package com.puntogris.whatdoiwear

import android.app.Application
import com.puntogris.whatdoiwear.di.AppComponent
import com.puntogris.whatdoiwear.di.DaggerAppComponent
import com.puntogris.whatdoiwear.di.InjectorProvider

class App : Application(), InjectorProvider {
    override  val component: AppComponent by lazy{
        DaggerAppComponent
            .factory()
            .create(applicationContext)
    }
}

