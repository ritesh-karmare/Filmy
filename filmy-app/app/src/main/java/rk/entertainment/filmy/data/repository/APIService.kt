package rk.entertainment.filmy.data.repository

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rk.entertainment.filmy.data.models.configuration.ConfigurationRes
import rk.entertainment.filmy.data.models.movieList.MoviesListResponse
import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes


interface APIService {
    /**
     * GET Methods
     */
    // Get TMDB configuration
    @GET(APIUtils.CONFIGURATION)
    fun getConfigurations(): Observable<ConfigurationRes>

    // Get now playing movies
    @GET(APIUtils.MOVIE + APIUtils.NOW_PLAYING)
    suspend fun getNowPlayingMovies(@Query("page") page: Int): MoviesListResponse

    // Get upcoming movies
    @GET(APIUtils.MOVIE + APIUtils.UPCOMING)
    suspend fun getUpcomingMovies(@Query("page") page: Int): MoviesListResponse

    // Get top rated movies
    @GET(APIUtils.MOVIE + APIUtils.TOP_RATED)
    suspend fun getTopRatedMovies(@Query("page") page: Int): MoviesListResponse

    // Get popular movies
    @GET(APIUtils.MOVIE + APIUtils.POPULAR)
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesListResponse

    // Search movies
    @GET(APIUtils.SEARCH_MOVIES)
    suspend fun getSearchedMovies(@Query("query") query: String, @Query("include_adult") includeAdult: Boolean, @Query("page") page: Int): MoviesListResponse

    // Get movie details
    @GET(APIUtils.MOVIE + "{movieId}")
    suspend fun getMovieDetails(
            @Path("movieId") movieId: Int,
            @Query("append_to_response") appendToResponse: String?): MovieDetailsRes
}