package rk.entertainment.filmy.ui.features.moviesListing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import rk.entertainment.filmy.data.network.Resource
import rk.entertainment.filmy.domain.useCase.GetMoviesListUseCase
import rk.entertainment.filmy.utils.Logs
import rk.entertainment.filmy.utils.MovieModuleTypes
import javax.inject.Inject

@HiltViewModel
class MoviesListingViewModel @Inject constructor(private val moviesListUseCase: GetMoviesListUseCase) :
    ViewModel() {

    private val TAG = "MoviesListingViewModel"

    private var page = 0
    private var totalPages = 1

    private val _movieListStateFlow = MutableStateFlow(MovieListState())
    val movieListStateFlow: StateFlow<MovieListState> = _movieListStateFlow

    fun getMovies(moduleTypes: MovieModuleTypes) {


        handlePageOffset(true)
        moviesListUseCase(moduleTypes, page).onEach {

            when (it) {
                is Resource.Loading -> _movieListStateFlow.emit(MovieListState(loading = true))

                is Resource.Success -> {
                    if(it.value != null) {
                        totalPages = it.value.totalPages
                        _movieListStateFlow.emit(MovieListState(movieDetails = it.value))
                    } else {
                        handlePageOffset(false)
                    }
                }

                is Resource.Error -> {
                    _movieListStateFlow.emit(MovieListState(error = it.errorMessage))
                    handlePageOffset(false)
                }
            }

        }.launchIn(viewModelScope)
    }

    // Increment/Decrement page offset for pagination
    private fun handlePageOffset(increment: Boolean) {
        Logs.i(TAG, "page:$page totalPages:$totalPages")
        if (increment) {
            if (page < totalPages) page += 1
        } else if (page > 1) page -= 1
    }

    fun resetPage() {
        page = 0
    }

    fun addMore() = page > 1

}