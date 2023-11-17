package com.sparklead.anipedia.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.sparklead.anipedia.model.AnimeDb
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Upsert
    suspend fun saveAnime(anime: AnimeDb)

    @Query("SELECT * FROM Anime")
    fun getSaveAnime(): Flow<List<AnimeDb>>

    @Delete
    suspend fun unSaveAnime(anime: AnimeDb)

    @Query("SELECT COUNT(*) FROM Anime WHERE title = :title")
    fun getAnimeCount(title: String) : Flow<Int>
}