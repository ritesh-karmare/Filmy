package rk.entertainment.filmy.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes
import rk.entertainment.filmy.data.network.DETAIL_TYPES
import rk.entertainment.filmy.domain.repository.MoviesRepository
import rk.entertainment.filmy.utils.Resource
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    operator fun invoke(movieId: Int): Flow<Resource<MovieDetailsRes>> {
        return flow {
            emit(Resource.Loading())
            val data = moviesRepository.getMovieDetails(movieId, DETAIL_TYPES)
            emit(Resource.Success(data))
        }
    }
}