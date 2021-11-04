package com.puntogris.smartwear.di

import com.puntogris.smartwear.feature_weather.data.data_source.FusedLocationClient
import com.puntogris.smartwear.feature_weather.data.data_source.local.AppDatabase
import com.puntogris.smartwear.feature_weather.data.data_source.local.LocationDao
import com.puntogris.smartwear.feature_weather.data.data_source.local.SharedPreferences
import com.puntogris.smartwear.feature_weather.data.data_source.remote.GeocodingApi
import com.puntogris.smartwear.feature_weather.data.data_source.remote.WeatherApi
import com.puntogris.smartwear.feature_weather.data.repository.LocationRepositoryImpl
import com.puntogris.smartwear.feature_weather.data.repository.WeatherRepositoryImpl
import com.puntogris.smartwear.feature_weather.domain.repository.DispatcherProvider
import com.puntogris.smartwear.feature_weather.domain.repository.LocationRepository
import com.puntogris.smartwear.feature_weather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WeatherModule {

    @Singleton
    @Provides
    fun providesLocationDao(appDatabase: AppDatabase) = appDatabase.locationDao()

    @Singleton
    @Provides
    fun providesLocationRepository(
        locationClient: FusedLocationClient,
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
    fun providesWeatherRepository(
        weatherApi: WeatherApi,
        sharedPreferences: SharedPreferences
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi, sharedPreferences)
    }
}