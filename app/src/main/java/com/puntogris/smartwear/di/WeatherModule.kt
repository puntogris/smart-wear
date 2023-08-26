package com.puntogris.smartwear.di

import com.puntogris.smartwear.BuildConfig
import com.puntogris.smartwear.core.utils.constants.HttpRoutes
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WeatherModule {

    @Singleton
    @Provides
    fun providesLocationDao(appDatabase: AppDatabase) = appDatabase.locationDao()

    @Singleton
    @Provides
    fun provideGeocodingApi(): GeocodingApi {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter(HttpRoutes.KEY, BuildConfig.LOCATIONIQ_API_KEY)
                    .addQueryParameter(HttpRoutes.FORMAT, HttpRoutes.JSON)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()

        return Retrofit.Builder()
            .baseUrl(HttpRoutes.LOCATIONIQ_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherAPi(): WeatherApi {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter(HttpRoutes.APPID, BuildConfig.OPEN_WEATHER_API_KEY)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()

        return Retrofit.Builder()
            .baseUrl(HttpRoutes.OPEN_WEATHER_API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

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