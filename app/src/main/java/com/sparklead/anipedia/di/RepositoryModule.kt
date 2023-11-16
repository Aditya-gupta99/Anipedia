package com.sparklead.anipedia.di

import com.sparklead.anipedia.service.AnimeService
import com.sparklead.anipedia.ui.home.AnimeListRepository
import com.sparklead.anipedia.ui.home.AnimeListRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun providesAnimeListRepository(service: AnimeService): AnimeListRepository =
        AnimeListRepositoryImp(service)

}