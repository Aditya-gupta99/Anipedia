package com.sparklead.anipedia.di

import android.content.Context
import androidx.room.Room
import com.sparklead.anipedia.dao.AnimeDao
import com.sparklead.anipedia.db.AnimeDatabase
import com.sparklead.anipedia.service.AnimeService
import com.sparklead.anipedia.serviceImp.AnimeServiceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }

    @Provides
    @Singleton
    fun providesAnimeService(client: HttpClient): AnimeService = AnimeServiceImp(client)

    @Provides
    @Singleton
    fun provideAnimeDatabase(@ApplicationContext appContext: Context): AnimeDatabase {
        return Room.databaseBuilder(
            appContext,
            AnimeDatabase::class.java,
            "Anime"
        ).fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun provideAnimeDao(animeDatabase: AnimeDatabase): AnimeDao {
        return animeDatabase.animeDao()
    }
}