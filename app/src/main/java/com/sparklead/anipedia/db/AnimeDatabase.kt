package com.sparklead.anipedia.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sparklead.anipedia.dao.AnimeDao
import com.sparklead.anipedia.dao.OfflineAnimeDao
import com.sparklead.anipedia.dao.OfflineTopAnimeDao
import com.sparklead.anipedia.model.AnimeDb
import com.sparklead.anipedia.model.OfflineAnimeDb
import com.sparklead.anipedia.model.OfflineTopAnimeDb

@Database(entities = [AnimeDb::class, OfflineAnimeDb::class, OfflineTopAnimeDb::class], version = 7)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    abstract fun offlineDao(): OfflineAnimeDao

    abstract fun topOfflineDao(): OfflineTopAnimeDao
}