package com.puntogris.whatdoiwear.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*

@InstallIn(SingletonComponent::class)
@Module
class WeatherModule {

    @Provides
    fun createKtorClient(): HttpClient{
        return HttpClient(Android){

        }
    }
}