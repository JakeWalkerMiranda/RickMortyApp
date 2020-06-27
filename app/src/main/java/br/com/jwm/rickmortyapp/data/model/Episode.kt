package br.com.jwm.rickmortyapp.data.model

data class Episode(
    private val id: Long,
    private val name: String,
    private val air_date: String,
    private val episode: String,
    private val characters: List<String>,
    private val url: String,
    private val created: String
)