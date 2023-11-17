package com.sparklead.anipedia.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Anime")
data class AnimeDb(
    @PrimaryKey(autoGenerate = true)
    val mal_id: Int?,

    val background: String?,

    val episodes: Int?,

    val images: String,

    val rank: Int?,

    val score: Double?,

    val synopsis: String?,

    val title: String?
) : Parcelable
