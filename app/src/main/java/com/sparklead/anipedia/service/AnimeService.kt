package com.sparklead.anipedia.service

import com.sparklead.anipedia.model.all_anime.AnimeModel
import com.sparklead.anipedia.model.all_anime.AnimeResponse

interface AnimeService {

    suspend fun getAllAnimeList() : AnimeModel

    suspend fun getTopAnimeList() : AnimeModel

    suspend fun getSearchAnimeList(text : String) : AnimeModel

}