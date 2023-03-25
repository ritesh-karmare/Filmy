package rk.entertainment.domain.useCase

import kotlinx.coroutines.flow.Flow
import rk.entertainment.common.data.Resource
import rk.entertainment.common.data.flowApiCall
import rk.entertainment.common.models.MovieModuleTypes
import rk.entertainment.common.models.movieList.MoviesListResponse
import rk.entertainment.common.utils.ENDPOINT_NOW_PLAYING
import rk.entertainment.common.utils.ENDPOINT_POPULAR
import rk.entertainment.common.utils.ENDPOINT_TOP_RATED
import rk.entertainment.common.utils.ENDPOINT_UPCOMING
import rk.entertainment.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesListUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(moduleTypes: MovieModuleTypes, pageNo: Int)
            : Flow<Resource<MoviesListResponse>> {

        return flowApiCall {
            val category = when (moduleTypes) {
                MovieModuleTypes.NOW_PLAYING -> {
                    ENDPOINT_NOW_PLAYING
                }
                MovieModuleTypes.UPCOMING -> {
                    ENDPOINT_UPCOMING
                }
                MovieModuleTypes.TOP_RATED -> {
                    ENDPOINT_TOP_RATED
                }
                MovieModuleTypes.POPULAR -> {
                    ENDPOINT_POPULAR
                }
            }

            repository.getMoviesListing(category, pageNo)
        }
    }
}