package br.com.jwm.rickmortyapp.ui.main.episode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.jwm.rickmortyapp.data.repository.MainRepository
import br.com.jwm.rickmortyapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class EpisodeViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getCharacterEpisodes(range: String) = liveData(Dispatchers.IO) {
        Log.e("JWM", "Range: $range")
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getCharacterEpisodes(range)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "An error has occurred!"))
        }
    }

    fun getEpisodes() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getEpisodes()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "An error has occurred!"))
        }
    }
}