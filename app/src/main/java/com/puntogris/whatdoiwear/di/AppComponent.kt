package com.puntogris.whatdoiwear.di

import android.content.Context
import com.puntogris.whatdoiwear.data.Repository
import com.puntogris.whatdoiwear.ui.MainFragmentViewModel
import com.puntogris.whatdoiwear.utils.PermissionsManager
import com.puntogris.whatdoiwear.utils.MySharedPreferences
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SharedPreferenceModule::class])
interface AppComponent {
    val permissionManager: PermissionsManager
    val repository: Repository
    val mainViewModel: MainFragmentViewModel
    val sharedPreferences: MySharedPreferences


    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance applicationContext: Context
        ): AppComponent
    }
}