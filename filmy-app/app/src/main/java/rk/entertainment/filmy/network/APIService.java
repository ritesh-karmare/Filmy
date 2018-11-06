package rk.entertainment.filmy.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rk.entertainment.filmy.models.configuration.ConfigurationRes;
import rk.entertainment.filmy.models.movieList.MoviesListResponse;
import rk.entertainment.filmy.models.moviesDetails.MovieDetailsRes;

public interface APIService {

    /**
     * GET Methods
     */

    // Get TMDB configuration
    @GET(APIUtils.CONFIGURATION)
    Observable<ConfigurationRes> getConfigurations(@Query("api_key") String apiKey);

    // Get now playing movies
    @GET(APIUtils.MOVIE + APIUtils.NOW_PLAYING)
    Observable<MoviesListResponse> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int page);

    // Get upcoming movies
    @GET(APIUtils.MOVIE + APIUtils.UPCOMING)
    Observable<MoviesListResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") int page);

    // Get top rated movies
    @GET(APIUtils.MOVIE + APIUtils.TOP_RATED)
    Observable<MoviesListResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    // Get popular movies
    @GET(APIUtils.MOVIE + APIUtils.POPULAR)
    Observable<MoviesListResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    // Search movies
    @GET(APIUtils.SEARCH_MOVIES)
    Observable<MoviesListResponse> getSearchedMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("include_adult") boolean includeAdult, @Query("page") int page);

    // Get movie details
    @GET(APIUtils.MOVIE + "{movieId}")
    Observable<MovieDetailsRes> getMovieDetails(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);

}