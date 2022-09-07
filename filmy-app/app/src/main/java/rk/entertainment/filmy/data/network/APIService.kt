package rk.entertainment.filmy.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rk.entertainment.filmy.data.models.movieList.MoviesListResponse
import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes


interface APIService {
    /**
     * GET Methods
     */

    // Get now playing movies
    @GET(ENDPOINT_MOVIE + ENDPOINT_NOW_PLAYING)
    suspend fun getNowPlayingMovies(@Query("page") page: Int): MoviesListResponse

    // Get upcoming movies
    @GET(ENDPOINT_MOVIE + ENDPOINT_UPCOMING)
    suspend fun getUpcomingMovies(@Query("page") page: Int): MoviesListResponse

    // Get top rated movies
    @GET(ENDPOINT_MOVIE + ENDPOINT_TOP_RATED)
    suspend fun getTopRatedMovies(@Query("page") page: Int): MoviesListResponse

    // Get popular movies
    @GET(ENDPOINT_MOVIE + ENDPOINT_POPULAR)
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesListResponse

    // Search movies
    @GET(ENDPOINT_SEARCH_MOVIES)
    suspend fun getSearchedMovies(@Query("query") query: String, @Query("include_adult") includeAdult: Boolean, @Query("page") page: Int): MoviesListResponse

    // Get movie details
    @GET(ENDPOINT_MOVIE + "{movieId}")
    suspend fun getMovieDetails(
            @Path("movieId") movieId: Int,
            @Query("append_to_response") appendToResponse: String?): MovieDetailsRes
}