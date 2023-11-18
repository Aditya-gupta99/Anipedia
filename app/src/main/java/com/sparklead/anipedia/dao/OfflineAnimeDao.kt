package com.sparklead.anipedia.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sparklead.anipedia.model.OfflineAnimeDb
import kotlinx.coroutines.flow.Flow

@Dao
interface OfflineAnimeDao {

    @Upsert
    suspend fun saveAnimeList(offlineAnimeDbList: List<OfflineAnimeDb>)

    @Query("SELECT * FROM OfflineAnime")
    fun getOfflineAnime() : Flow<List<OfflineAnimeDb>>
}