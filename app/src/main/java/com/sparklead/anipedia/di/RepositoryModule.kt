package com.sparklead.anipedia.di

import com.sparklead.anipedia.dao.AnimeDao
import com.sparklead.anipedia.dao.OfflineAnimeDao
import com.sparklead.anipedia.dao.OfflineTopAnimeDao
import com.sparklead.anipedia.service.AnimeService
import com.sparklead.anipedia.ui.detail.AnimeDbRepository
import com.sparklead.anipedia.ui.detail.AnimeDbRepositoryImp
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
    fun providesAnimeListRepository(
        service: AnimeService,
        animeDao: OfflineAnimeDao,
        offlineAnimeDao: OfflineTopAnimeDao
    ): AnimeListRepository =
        AnimeListRepositoryImp(service, animeDao, offlineAnimeDao)

    @Provides
    fun providesAnimeDbRepository(dao: AnimeDao): AnimeDbRepository =
        AnimeDbRepositoryImp(dao)
}