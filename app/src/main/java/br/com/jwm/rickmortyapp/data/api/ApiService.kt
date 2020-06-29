package br.com.jwm.rickmortyapp.data.api

import br.com.jwm.rickmortyapp.data.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("character")
    suspend fun getCharacters(): CharacterList

    @GET("character/{id}")
    fun getCharactersDetails(@Path("id") id : String): Call<CharacterDetails>

    @GET("episode")
    suspend fun getEpisodes(): EpisodeList

    @GET("episode/{range}")
    suspend fun getCharacterEpisodes(@Path("range") range: String): List<Episode>

    @GET("location")
    suspend fun getLocations(): List<Location>
}