package com.puntogris.whatdoiwear.di

import android.content.Context
import android.content.SharedPreferences
import com.puntogris.whatdoiwear.utils.Constants
import dagger.Module
import dagger.Provides

@Module
object SharedPreferenceModule {
    @JvmStatic @Provides
    fun provideSharedPreferenceName (context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.SP_FILE,Context.MODE_PRIVATE)

}


