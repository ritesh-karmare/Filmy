package rk.entertainment.filmy.data.repository

import rk.entertainment.filmy.data.models.movieList.MoviesListResponse
import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes

class MoviesRepository {

    private val apiService = APIClient.getClient()?.create(APIService::class.java)

    suspend fun getNowPlayingMovies(page: Int): MoviesListResponse? {
        return apiService?.getNowPlayingMovies(page)
    }

    suspend fun getUpcomingMovies(page: Int): MoviesListResponse? {
        return apiService?.getUpcomingMovies(page)
    }

    suspend fun getTopRatedMovies(page: Int): MoviesListResponse? {
        return apiService?.getTopRatedMovies(page)
    }

    suspend fun getPopularMovies(page: Int): MoviesListResponse? {
        return apiService?.getPopularMovies(page)
    }

    suspend fun getMovieDetails(movieId: Int, appendToRespose: String): MovieDetailsRes? {
        return apiService?.getMovieDetails(movieId, appendToRespose)
    }

    suspend fun getSearchMovieData(query: String, includeAdult: Boolean, page: Int): MoviesListResponse? {
        return apiService?.getSearchedMovies(query, includeAdult, page)

    }

}