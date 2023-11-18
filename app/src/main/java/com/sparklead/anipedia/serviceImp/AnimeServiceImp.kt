package com.sparklead.anipedia.serviceImp

import com.sparklead.anipedia.model.all_anime.AnimeModel
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import com.sparklead.anipedia.remote.HttpRoutes
import com.sparklead.anipedia.service.AnimeService
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AnimeServiceImp(private val client: HttpClient) : AnimeService {

    override suspend fun getAllAnimeList(): AnimeModel {
        return try {
            client.get {
                url(HttpRoutes.ALL_ANIME_LIST)
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTopAnimeList(): AnimeModel {
        return try {
            client.get {
                url(HttpRoutes.TOP_ANIME_LIST)
                parameter("limit",10)
                contentType(ContentType.Application.Json)
            }
        } catch (e : Exception) {
            throw e
        }
    }

    override suspend fun getSearchAnimeList(text: String): AnimeModel {
        return try {
            client.get {
                url(HttpRoutes.SEARCH_ANIME_LIST)
                parameter("q",text)
                parameter("limit",20)
                contentType(ContentType.Application.Json)
            }
        } catch (e : Exception) {
            throw e
        }
    }
}