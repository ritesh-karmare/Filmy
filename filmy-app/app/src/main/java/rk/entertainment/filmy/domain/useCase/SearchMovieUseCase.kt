package rk.entertainment.filmy.domain.useCase

import kotlinx.coroutines.flow.Flow
import rk.entertainment.filmy.data.models.movieList.MoviesListResponse
import rk.entertainment.filmy.data.network.Resource
import rk.entertainment.filmy.data.network.flowApiCall
import rk.entertainment.filmy.domain.repository.MoviesRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(searchQuery: String, pageNo: Int)
            : Flow<Resource<MoviesListResponse>> {

        return flowApiCall {
            repository.getSearchMovieData(searchQuery, pageNo)
        }
    }
}