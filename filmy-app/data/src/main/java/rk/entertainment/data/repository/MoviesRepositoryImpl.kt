package rk.entertainment.data.repository

import rk.entertainment.common.models.movieList.MoviesListResponse
import rk.entertainment.common.models.moviesDetails.MovieDetailsRes
import rk.entertainment.data.network.APIService
import rk.entertainment.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val apiService: APIService) :
    MoviesRepository {

    override suspend fun getMoviesListing(category: String, page: Int): MoviesListResponse {
        return apiService.getMoviesList(category, page)
    }

    override suspend fun getMovieDetails(movieId: Int, appendToResponse: String): MovieDetailsRes {
        return apiService.getMovieDetails(movieId, appendToResponse)
    }

    override suspend fun getSearchMovieData(
        query: String,
        page: Int
    ): MoviesListResponse {
        return apiService.getSearchedMovies(query, page)
    }

}