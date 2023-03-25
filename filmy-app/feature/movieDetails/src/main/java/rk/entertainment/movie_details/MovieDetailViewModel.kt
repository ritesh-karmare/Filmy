package rk.entertainment.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import rk.entertainment.common.data.Resource
import rk.entertainment.domain.useCase.GetMovieDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val getMovieDetailsUseCase: GetMovieDetailsUseCase) :
    ViewModel() {

    private val _movieDetailsStateFlow = MutableStateFlow(MovieDetailsState())
    val movieDetailsStateFlow: StateFlow<MovieDetailsState> = _movieDetailsStateFlow

    fun getMovieDetails(movieId: Int) {
        getMovieDetailsUseCase(movieId)
            .onEach {
                when (it) {
                    is Resource.Loading ->
                        _movieDetailsStateFlow.emit(MovieDetailsState(loading = true))
                    is Resource.Success ->
                        _movieDetailsStateFlow.emit(MovieDetailsState(movieDetails = it.value))
                    is Resource.Error ->
                        _movieDetailsStateFlow.emit(MovieDetailsState(error = it.errorMessage))
                }
            }.launchIn(viewModelScope)
    }
}