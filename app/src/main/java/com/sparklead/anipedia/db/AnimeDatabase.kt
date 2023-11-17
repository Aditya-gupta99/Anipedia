package com.sparklead.anipedia.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sparklead.anipedia.dao.AnimeDao
import com.sparklead.anipedia.model.all_anime.AnimeResponse

@Database(entities = [AnimeResponse::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    companion object {

        @Volatile
        var INSTANCE: AnimeDatabase? = null

        fun getDatabaseInstance(context: Context): AnimeDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val roomDatabaseInstance =
                    Room.databaseBuilder(context, AnimeDatabase::class.java, "Anime")
                        .allowMainThreadQueries()
                        .build()
                INSTANCE = roomDatabaseInstance
                return roomDatabaseInstance
            }

        }

    }

}