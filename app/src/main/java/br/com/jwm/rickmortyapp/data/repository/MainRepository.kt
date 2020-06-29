package br.com.jwm.rickmortyapp.data.repository

import br.com.jwm.rickmortyapp.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getCharacters() = apiHelper.getCharacters()
    suspend fun getCharacterEpisodes(range: String) = apiHelper.getCharacterEpisodes(range)
    suspend fun getEpisodes() = apiHelper.getEpisodes()
    suspend fun getLocations() = apiHelper.getLocations()
}