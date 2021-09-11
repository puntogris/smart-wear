package com.puntogris.whatdoiwear.data.local

import android.content.Context
import androidx.room.Room
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
    fun providesLocationDao(locationDatabase: LocationDatabase): LocationDao {
        return locationDatabase.locationDao()
    }

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