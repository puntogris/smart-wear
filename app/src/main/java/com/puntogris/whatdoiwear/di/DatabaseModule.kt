package com.puntogris.whatdoiwear.di

import android.content.Context
import androidx.room.Room
import com.puntogris.whatdoiwear.data.local.LocationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun providesLocationDao(locationDatabase: LocationDatabase) = locationDatabase.locationDao()


    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext appContext: Context): LocationDatabase {
        return Room
            .databaseBuilder(
                appContext,
                LocationDatabase::class.java,
                "location_table"
            )
            .build()
    }
}