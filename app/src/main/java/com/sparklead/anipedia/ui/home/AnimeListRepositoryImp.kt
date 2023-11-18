package com.sparklead.anipedia.ui.home

import com.sparklead.anipedia.dao.OfflineAnimeDao
import com.sparklead.anipedia.dao.OfflineTopAnimeDao
import com.sparklead.anipedia.model.OfflineAnimeDb
import com.sparklead.anipedia.model.OfflineTopAnimeDb
import com.sparklead.anipedia.model.all_anime.AnimeModel
import com.sparklead.anipedia.service.AnimeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeListRepositoryImp @Inject constructor(
    private val service: AnimeService,
    private val animeDao: OfflineAnimeDao,
    private val offlineTopAnimeDao: OfflineTopAnimeDao
) :
    AnimeListRepository {

    override fun getAllAnimeList(): Flow<AnimeModel> {
        return flow {
            emit(service.getAllAnimeList())
        }
    }

    override fun getTopAnimeList(): Flow<AnimeModel> {
        return flow {
            emit(service.getTopAnimeList())
        }
    }

    override fun getSearch(text: String): Flow<AnimeModel> {
        return flow {
            emit(service.getSearchAnimeList(text))
        }
    }

    override suspend fun saveOfflineAnime(list: List<OfflineAnimeDb>) {
        animeDao.saveAnimeList(list)
    }

    override fun getOfflineAnime(): Flow<List<OfflineAnimeDb>> {
        return animeDao.getOfflineAnime()
    }

    override suspend fun saveOfflineTopAnime(list: List<OfflineTopAnimeDb>) {
        offlineTopAnimeDao.saveOfflineTopAnime(list)
    }

    override fun getOfflineTopAnime(): Flow<List<OfflineTopAnimeDb>> {
        return offlineTopAnimeDao.getTopOfflineAnime()
    }
}