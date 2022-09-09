package rk.entertainment.filmy.domain.repository

import rk.entertainment.filmy.data.models.movieList.MoviesListResponse
import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes

interface MoviesRepository {

    suspend fun getMoviesListing(category: String, page: Int): MoviesListResponse

    suspend fun getMovieDetails(movieId: Int, appendToResponse: String): MovieDetailsRes

    suspend fun getSearchMovieData(query: String, page: Int): MoviesListResponse
}