package com.sparklead.anipedia.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.sparklead.anipedia.model.all_anime.AnimeModel
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Upsert
    suspend fun saveAnime(anime: AnimeModel)

    @Query("SELECT * FROM anime")
    fun getSaveAnime(): Flow<List<AnimeResponse>>

    @Delete
    suspend fun unSaveAnime(anime: AnimeModel)
}