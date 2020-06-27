package br.com.jwm.rickmortyapp.data.api

import br.com.jwm.rickmortyapp.data.model.Character
import br.com.jwm.rickmortyapp.data.model.CharacterList
import br.com.jwm.rickmortyapp.data.model.Episode
import br.com.jwm.rickmortyapp.data.model.Location
import retrofit2.http.GET


interface ApiService {

    @GET("character")
    suspend fun getCharacters(): CharacterList

    @GET("episode")
    suspend fun getEpisodes(): List<Episode>

    @GET("location")
    suspend fun getLocations(): List<Location>
}