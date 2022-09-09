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

    // Get movies List
    @GET("$ENDPOINT_MOVIE{type}")
    suspend fun getMoviesList(
        @Path("type") type: String,
        @Query("page") page: Int
    ): MoviesListResponse

    // Search movies
    @GET(ENDPOINT_SEARCH_MOVIES)
    suspend fun getSearchedMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MoviesListResponse

    // Get movie details
    @GET("$ENDPOINT_MOVIE{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("append_to_response") appendToResponse: String?
    ): MovieDetailsRes
}