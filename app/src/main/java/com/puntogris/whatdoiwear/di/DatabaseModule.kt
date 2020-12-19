package com.puntogris.whatdoiwear.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.puntogris.whatdoiwear.data.LocationDao
import com.puntogris.whatdoiwear.data.LocationDatabase
import com.puntogris.whatdoiwear.model.LastLocation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.Executors
import javax.inject.Singleton
import javax.security.auth.callback.Callback

@InstallIn(ApplicationComponent::class)
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