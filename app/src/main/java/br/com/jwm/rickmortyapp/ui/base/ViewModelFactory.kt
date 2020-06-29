package br.com.jwm.rickmortyapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.jwm.rickmortyapp.data.api.ApiHelper
import br.com.jwm.rickmortyapp.data.repository.MainRepository
import br.com.jwm.rickmortyapp.ui.main.character.CharacterViewModel
import br.com.jwm.rickmortyapp.ui.main.episode.EpisodeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java))
            return CharacterViewModel(MainRepository(apiHelper)) as T

        else if (modelClass.isAssignableFrom(EpisodeViewModel::class.java))
            return EpisodeViewModel(MainRepository(apiHelper)) as T

        throw IllegalArgumentException("Unknown class name")
    }
}