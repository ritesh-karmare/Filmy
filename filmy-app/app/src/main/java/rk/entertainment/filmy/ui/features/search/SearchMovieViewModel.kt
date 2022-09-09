package rk.entertainment.filmy.ui.features.search

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import rk.entertainment.filmy.data.network.Resource
import rk.entertainment.filmy.domain.useCase.SearchMovieUseCase
import rk.entertainment.filmy.ui.features.moviesListing.MovieListState
import rk.entertainment.filmy.utils.Logs
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val searchMovieUseCase: SearchMovieUseCase) :
    ViewModel() {

    private val TAG = "SearchMovieViewModel"

    private var page = 0
    private var totalPages = 1
    private var previousSearchQuery = ""

    private val _movieListStateFlow = MutableStateFlow(MovieListState())
    val movieListStateFlow: StateFlow<MovieListState> = _movieListStateFlow

    fun searchmovies(query: String) {

        if(TextUtils.isEmpty(query)||!previousSearchQuery.equals(query)) resetPage()
        previousSearchQuery = query

        handlePageOffset(true)
        searchMovieUseCase(query, page).onEach {

            when (it) {

                is Resource.Loading -> {
                    _movieListStateFlow.emit(MovieListState(loading = true))
                }

                is Resource.Error -> {
                    _movieListStateFlow.emit(MovieListState(error = it.errorMessage))
                    handlePageOffset(false)
                }

                is Resource.Success -> {
                    totalPages = it.value.totalPages
                    Logs.i(TAG, "page:$page totalPages:$totalPages  onSuccess")
                    _movieListStateFlow.emit(MovieListState(movieDetails = it.value))
                }
            }


            /*  when (it) {
                  is Resource.Loading -> _movieListStateFlow.emit(MovieListState(loading = true))

                  is Resource.Success -> {
                      if(it.data != null) {
                              totalPages = it.data.totalPages
                          Logs.i(TAG, "page:$page totalPages:$totalPages  onSuccess")
                          _movieListStateFlow.emit(MovieListState(movieDetails = it.data))
                      } else {
                          handlePageOffset(false)
                      }
                  }

                  is Resource.Error -> {
                      _movieListStateFlow.emit(MovieListState(error = it.message))
                      handlePageOffset(false)
                  }
              }*/

        }.launchIn(viewModelScope)
    }

    // Increment/Decrement page offset for pagination
    private fun handlePageOffset(increment: Boolean) {
        Logs.i(TAG, "page:$page totalPages:$totalPages")
        if(increment) {
            if(page < totalPages) page += 1
        } else if(page > 1) page -= 1
        Logs.i(TAG, "final page:$page totalPages:$totalPages")
    }

    private fun resetPage() {
        page = 0
    }

}