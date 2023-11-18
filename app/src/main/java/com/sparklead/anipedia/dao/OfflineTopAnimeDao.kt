package com.sparklead.anipedia.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sparklead.anipedia.model.OfflineAnimeDb
import com.sparklead.anipedia.model.OfflineTopAnimeDb
import kotlinx.coroutines.flow.Flow

@Dao
interface OfflineTopAnimeDao {

    @Upsert
    suspend fun saveOfflineTopAnime(offlineTopAnimeDb: List<OfflineTopAnimeDb>)

    @Query("SELECT * FROM OfflineTopAnime ORDER BY rank ASC")
    fun getTopOfflineAnime(): Flow<List<OfflineTopAnimeDb>>

}