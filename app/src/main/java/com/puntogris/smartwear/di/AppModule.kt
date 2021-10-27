package com.puntogris.smartwear.di

import android.content.Context
import androidx.room.Room
import com.puntogris.smartwear.common.StandardDispatchers
import com.puntogris.smartwear.data.data_source.LocationClient
import com.puntogris.smartwear.data.data_source.local.AppDatabase
import com.puntogris.smartwear.data.data_source.local.LocationDao
import com.puntogris.smartwear.data.data_source.remote.GeocodingApi
import com.puntogris.smartwear.data.data_source.remote.WeatherApi
import com.puntogris.smartwear.data.repository.LocationRepositoryImpl
import com.puntogris.smartwear.data.repository.WeatherRepositoryImpl
import com.puntogris.smartwear.domain.repository.DispatcherProvider
import com.puntogris.smartwear.domain.repository.LocationRepository
import com.puntogris.smartwear.domain.repository.WeatherRepository
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
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            )
            .build()
    }

    @Singleton
    @Provides
    fun providesLocationDao(appDatabase: AppDatabase) = appDatabase.locationDao()

    @Singleton
    @Provides
    fun provideKtorClient(): HttpClient {
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

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider{
        return StandardDispatchers()
    }

    @Singleton
    @Provides
    fun providesLocationRepository(
        locationClient: LocationClient,
        locationDao: LocationDao,
        geocodingApi: GeocodingApi,
        dispatcherProvider: DispatcherProvider
    ): LocationRepository {
        return LocationRepositoryImpl(
            locationClient = locationClient,
            locationDao = locationDao,
            geocodingApi = geocodingApi,
            dispatchers = dispatcherProvider
        )
    }

    @Singleton
    @Provides
    fun providesWeatherRepository(weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi)
    }
}