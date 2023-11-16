package com.sparklead.anipedia.ui.home

import com.sparklead.anipedia.model.all_anime.AnimeModel
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import com.sparklead.anipedia.service.AnimeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeListRepositoryImp @Inject constructor(private val service: AnimeService) :
    AnimeListRepository {

    override fun getAllAnimeList(): Flow<AnimeModel> {
        return flow {
            emit(service.getAllAnimeList())
        }
    }
}