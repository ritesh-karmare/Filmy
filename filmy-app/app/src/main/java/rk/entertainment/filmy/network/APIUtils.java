package rk.entertainment.filmy.network;

import rk.entertainment.filmy.BuildConfig;

public class APIUtils {

    public final static String API_KEY = BuildConfig.API_KEY;

    final static String BASE_URL = "https://api.themoviedb.org/3/";

    final static String CONFIGURATION = "configuration";

    final static String SEARCH_MOVIES = "search/movie/";

    final static String MOVIE = "movie/";

    final static String NOW_PLAYING = "now_playing";

    final static String UPCOMING = "upcoming";

    final static String TOP_RATED = "top_rated";

    final static String POPULAR = "popular";

    public final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    //  "w92", "w154", "w185", "w342", "w500", "w780", "original"
    public final static String POSTER_IMAGE_SIZE = "w342";

    // "w300", "w780", "w1280", "original"
    public final static String BACKDROP_IMAGE_SIZE = "w780";

    //  "w45", "w185", "h632", "original"
    //Profile size images
}
