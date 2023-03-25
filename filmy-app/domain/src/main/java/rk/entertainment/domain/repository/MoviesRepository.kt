package rk.entertainment.domain.repository

import rk.entertainment.common.models.movieList.MoviesListResponse
import rk.entertainment.common.models.moviesDetails.MovieDetailsRes

interface MoviesRepository {

    suspend fun getMoviesListing(category: String, page: Int): MoviesListResponse

    suspend fun getMovieDetails(movieId: Int, appendToResponse: String): MovieDetailsRes

    suspend fun getSearchMovieData(query: String, page: Int): MoviesListResponse
}