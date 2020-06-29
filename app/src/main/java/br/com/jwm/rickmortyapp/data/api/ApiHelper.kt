package br.com.jwm.rickmortyapp.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getCharacters() = apiService.getCharacters()
    suspend fun getCharacterEpisodes(range: String) = apiService.getCharacterEpisodes(range)
    suspend fun getEpisodes() = apiService.getEpisodes()
    suspend fun getLocations() = apiService.getLocations()
}