package rk.entertainment.filmy.domain.useCase

import kotlinx.coroutines.flow.Flow
import rk.entertainment.filmy.data.models.movieList.MoviesListResponse
import rk.entertainment.filmy.data.network.Resource
import rk.entertainment.filmy.data.network.flowApiCall
import rk.entertainment.filmy.domain.repository.MoviesRepository
import rk.entertainment.filmy.utils.MovieModuleTypes
import javax.inject.Inject

class GetMoviesListUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(moduleTypes: MovieModuleTypes, pageNo: Int)
            : Flow<Resource<MoviesListResponse>> {

        return flowApiCall {

            val data = repository.getSearchMovieData("rock",pageNo)
            data

           /* val category = when (moduleTypes) {
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

            repository.getMoviesListing(category, pageNo)*/
        }
    }
}