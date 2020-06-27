package br.com.jwm.rickmortyapp.data.model

data class Location(
    private val id: Int,
    private val name: String,
    private val type: String,
    private val dimension: String,
    private val residents: List<String>,
    private val url: String,
    private val created: String
)