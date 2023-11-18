package com.sparklead.anipedia.ui.home

import com.sparklead.anipedia.model.OfflineAnimeDb
import com.sparklead.anipedia.model.OfflineTopAnimeDb
import com.sparklead.anipedia.model.all_anime.AnimeModel
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import kotlinx.coroutines.flow.Flow

interface AnimeListRepository {

    fun getAllAnimeList() : Flow<AnimeModel>

    fun getTopAnimeList() : Flow<AnimeModel>

    fun getSearch(text: String) : Flow<AnimeModel>

    suspend fun saveOfflineAnime(list : List<OfflineAnimeDb>)

    fun getOfflineAnime() : Flow<List<OfflineAnimeDb>>

    suspend fun saveOfflineTopAnime(list: List<OfflineTopAnimeDb>)

    fun getOfflineTopAnime() : Flow<List<OfflineTopAnimeDb>>
}