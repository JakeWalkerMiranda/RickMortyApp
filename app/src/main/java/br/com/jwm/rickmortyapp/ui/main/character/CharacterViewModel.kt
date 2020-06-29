package br.com.jwm.rickmortyapp.ui.main.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.jwm.rickmortyapp.data.repository.MainRepository
import br.com.jwm.rickmortyapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CharacterViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getCharacters() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getCharacters()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "An error has occurred!"))
        }
    }
}