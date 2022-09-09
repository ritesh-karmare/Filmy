package rk.entertainment.filmy.domain.useCase

import kotlinx.coroutines.flow.Flow
import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes
import rk.entertainment.filmy.data.network.DETAIL_TYPES
import rk.entertainment.filmy.data.network.ResultWrapper
import rk.entertainment.filmy.data.network.flowApiCall
import rk.entertainment.filmy.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    operator fun invoke(movieId: Int): Flow<ResultWrapper<MovieDetailsRes>> {
        return flowApiCall { moviesRepository.getMovieDetails(movieId, DETAIL_TYPES) }
    }
}