package com.puntogris.whatdoiwear.di

import android.content.Context
import androidx.room.Room
import com.puntogris.whatdoiwear.data.data_source.local.LocationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

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

    @Provides
    fun providesLocationDao(locationDatabase: LocationDatabase) = locationDatabase.locationDao()

    @Singleton
    @Provides
    fun createKtorClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}