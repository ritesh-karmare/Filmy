package rk.entertainment.domain.useCase

import kotlinx.coroutines.flow.Flow
import rk.entertainment.common.data.Resource
import rk.entertainment.common.data.flowApiCall
import rk.entertainment.common.models.movieList.MoviesListResponse
import rk.entertainment.domain.repository.MoviesRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(searchQuery: String, pageNo: Int)
            : Flow<Resource<MoviesListResponse>> {

        return flowApiCall {
            repository.getSearchMovieData(searchQuery, pageNo)
        }
    }
}