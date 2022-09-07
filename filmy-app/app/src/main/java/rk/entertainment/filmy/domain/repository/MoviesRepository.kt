package rk.entertainment.filmy.domain.repository

import rk.entertainment.filmy.data.models.movieList.MoviesListResponse
import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes

interface MoviesRepository {

    suspend fun getNowPlayingMovies(page: Int): MoviesListResponse

    suspend fun getUpcomingMovies(page: Int): MoviesListResponse

    suspend fun getTopRatedMovies(page: Int): MoviesListResponse

    suspend fun getPopularMovies(page: Int): MoviesListResponse

    suspend fun getMovieDetails(movieId: Int, appendToResponse: String): MovieDetailsRes

    suspend fun getSearchMovieData(
        query: String,
        includeAdult: Boolean,
        page: Int
    ): MoviesListResponse
}