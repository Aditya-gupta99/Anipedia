package com.sparklead.anipedia.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "OfflineTopAnime")
data class OfflineTopAnimeDb(
    @PrimaryKey(autoGenerate = true)
    val mal_id: Int? = null,

    val background: String?,

    val episodes: Int?,

    val images: String,

    val rank: Int?,

    val score: Double?,

    val synopsis: String?,

    val title: String?
) : Parcelable
