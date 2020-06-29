package br.com.jwm.rickmortyapp.data.model

data class CharacterDetails(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationBase,
    val location: LocationBase,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)