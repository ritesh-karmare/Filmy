package rk.entertainment.filmy.view.features.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import rk.entertainment.filmy.data.repository.MoviesRepository
import rk.entertainment.filmy.utils.RemoteErrorEmitter
import rk.entertainment.filmy.utils.callSafeApi

class MovieDetailViewModel() : ViewModel(), RemoteErrorEmitter {

    private val moviesRepository = MoviesRepository()
    private val detailTypes = "images,videos,recommendations"

    private val _errorListener = MutableLiveData<String>()
    val errorListener: LiveData<String>
        get() = _errorListener


    fun getMovieDetails(movieId: Int) = liveData {
        val response = callSafeApi(this@MovieDetailViewModel) {
            moviesRepository.getMovieDetails(movieId, detailTypes)
        }

        if (response != null)
            emit(response)
    }

    override fun onError(msg: String) {
        _errorListener.value = msg
    }
}