package br.com.jwm.rickmortyapp.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jwm.rickmortyapp.data.model.Character


class SharedViewModel : ViewModel() {
    val selectedCharacter = MutableLiveData<Character>()

    fun selectCharacter(character: Character) {
        selectedCharacter.value = character
    }
}