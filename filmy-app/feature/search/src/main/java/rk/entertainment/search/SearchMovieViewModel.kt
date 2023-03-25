package rk.entertainment.search

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import rk.entertainment.common.data.Resource
import rk.entertainment.common.ui.MovieListState
import rk.entertainment.common.utils.Logs
import rk.entertainment.domain.useCase.SearchMovieUseCase
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val searchMovieUseCase: SearchMovieUseCase) :
    ViewModel() {

    var page = 0
        private set

    private var previousSearchQuery = ""

    private val _movieListStateFlow = MutableStateFlow(MovieListState())
    val movieListStateFlow: StateFlow<MovieListState> = _movieListStateFlow

    fun searchMovies(query: String) {

        if(TextUtils.isEmpty(query)||previousSearchQuery != query) resetPage()
        previousSearchQuery = query

        handlePageOffset(true)
        searchMovieUseCase(query, page).onEach {

            when (it) {
                is Resource.Loading -> {
                    _movieListStateFlow.emit(MovieListState(loading = true))
                }

                is Resource.Error -> {
                    Logs.i("TAG", "searchMovies: error")
                    _movieListStateFlow.emit(MovieListState(error = it.errorMessage))
                    handlePageOffset(false)
                }

                is Resource.Success -> {
                    Logs.i("TAG", "searchMovies: success")
                    _movieListStateFlow.emit(MovieListState(movieDetails = it.value))
                }
            }
        }.launchIn(viewModelScope)
    }

    // Increment/Decrement page offset for pagination
    private fun handlePageOffset(increment: Boolean) {
        if(increment)
            page += 1
        else if(page > 1)
            page -= 1
    }

    private fun resetPage() {
        page = 0
    }

}