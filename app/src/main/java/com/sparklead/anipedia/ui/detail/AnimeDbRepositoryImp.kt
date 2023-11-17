package com.sparklead.anipedia.ui.detail

import com.sparklead.anipedia.dao.AnimeDao
import com.sparklead.anipedia.model.AnimeDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeDbRepositoryImp @Inject constructor(private val animeDao: AnimeDao) : AnimeDbRepository {

    override suspend fun saveAnimeDb(animeDb: AnimeDb) {
        animeDao.saveAnime(animeDb)
    }

    override suspend fun unSaveAnimeDb(animeDb: AnimeDb) {
        animeDao.unSaveAnime(animeDb)
    }

    override suspend fun getAnimeCount(title: String): Flow<Int> {
        return animeDao.getAnimeCount(title)
    }
}