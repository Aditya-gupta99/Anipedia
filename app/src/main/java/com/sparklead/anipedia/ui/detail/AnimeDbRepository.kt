package com.sparklead.anipedia.ui.detail

import com.sparklead.anipedia.model.AnimeDb
import kotlinx.coroutines.flow.Flow

interface AnimeDbRepository {

    suspend fun saveAnimeDb(animeDb: AnimeDb)

    suspend fun unSaveAnimeDb(animeDb: AnimeDb)

    suspend fun getAnimeCount(title: String) : Flow<Int>

    fun getAllAnime() : Flow<List<AnimeDb>>
}