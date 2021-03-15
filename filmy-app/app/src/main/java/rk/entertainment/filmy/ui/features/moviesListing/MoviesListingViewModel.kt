package rk.entertainment.filmy.ui.features.moviesListing

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import rk.entertainment.filmy.data.models.movieList.MoviesListResponse
import rk.entertainment.filmy.data.repository.MoviesRepository
import rk.entertainment.filmy.utils.MovieModuleTypes
import rk.entertainment.filmy.utils.MovieModuleTypes.*
import rk.entertainment.filmy.utils.RemoteErrorEmitter
import rk.entertainment.filmy.utils.callSafeApi
import timber.log.Timber

class MoviesListingViewModel : ViewModel(), RemoteErrorEmitter {

    private val moviesRepository = MoviesRepository()
    private var page = 0
    private var totalPages = 1
    private val INCLUDE_ADULT = false
    private var previousSearchQuery = ""

    private val _errorListener = MutableLiveData<String>()
    val errorListener: LiveData<String>
        get() = _errorListener


    fun getMovies(moduleTypes: MovieModuleTypes, query: String): LiveData<MoviesListResponse> = liveData {

        if (moduleTypes == SEARCH) {
            if (TextUtils.isEmpty(query) || !previousSearchQuery.equals(query)) resetPage()
            previousSearchQuery = query
        }

        handlePageOffset(true)
        val response = callSafeApi(this@MoviesListingViewModel) {

            when (moduleTypes) {
                NOW_PLAYING -> {
                    moviesRepository.getNowPlayingMovies(page)
                }
                UPCOMING -> {
                    moviesRepository.getUpcomingMovies(page)
                }
                TOP_RATED -> {
                    moviesRepository.getTopRatedMovies(page)
                }
                POPULAR -> {
                    moviesRepository.getPopularMovies(page)
                }
                SEARCH -> {
                    moviesRepository.getSearchMovieData(query, INCLUDE_ADULT, page)
                }
            }
        }

        if (response != null) {
            totalPages = response.totalPages
            emit(response)
        }
    }

    // Increment/Decrement page offset for pagination
    private fun handlePageOffset(increment: Boolean) {
        Timber.i("page:$page totalPages:$totalPages")
        if (increment) {
            if (page < totalPages) page += 1
        } else if (page > 1) page -= 1
    }

    fun resetPage() {
        page = 0
    }

    fun addMore() = page > 1

    override fun onError(msg: String) {
        Timber.i("onError msg : $msg")
        _errorListener.value = msg
        handlePageOffset(false)
    }
}