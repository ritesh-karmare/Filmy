package rk.entertainment.filmy.data.repository

import rk.entertainment.filmy.data.models.movieList.MoviesListResponse
import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes
import rk.entertainment.filmy.data.network.APIService
import rk.entertainment.filmy.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val apiService: APIService) :
    MoviesRepository {

    override suspend fun getNowPlayingMovies(page: Int): MoviesListResponse {
        return apiService.getNowPlayingMovies(page)
    }

    override suspend fun getUpcomingMovies(page: Int): MoviesListResponse {
        return apiService.getUpcomingMovies(page)
    }

    override suspend fun getTopRatedMovies(page: Int): MoviesListResponse {
        return apiService.getTopRatedMovies(page)
    }

    override suspend fun getPopularMovies(page: Int): MoviesListResponse {
        return apiService.getPopularMovies(page)
    }

    override suspend fun getMovieDetails(movieId: Int, appendToResponse: String): MovieDetailsRes {
        return apiService.getMovieDetails(movieId, appendToResponse)
    }

    override suspend fun getSearchMovieData(
        query: String,
        includeAdult: Boolean,
        page: Int
    ): MoviesListResponse {
        return apiService.getSearchedMovies(query, includeAdult, page)
    }

}