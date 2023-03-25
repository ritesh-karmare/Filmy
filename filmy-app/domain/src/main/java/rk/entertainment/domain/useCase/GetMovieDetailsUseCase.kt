package rk.entertainment.domain.useCase

import kotlinx.coroutines.flow.Flow
import rk.entertainment.common.data.Resource
import rk.entertainment.common.data.flowApiCall
import rk.entertainment.common.models.moviesDetails.MovieDetailsRes
import rk.entertainment.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    val DETAIL_TYPES = "images,videos,recommendations"

    operator fun invoke(movieId: Int): Flow<Resource<MovieDetailsRes>> {
        return flowApiCall { moviesRepository.getMovieDetails(movieId, DETAIL_TYPES) }
    }
}