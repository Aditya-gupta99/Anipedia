package com.sparklead.anipedia.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sparklead.anipedia.dao.AnimeDao
import com.sparklead.anipedia.model.AnimeDb

@Database(entities = [AnimeDb::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

}