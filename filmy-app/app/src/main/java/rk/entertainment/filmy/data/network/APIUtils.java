package rk.entertainment.filmy.data.network;

import rk.entertainment.filmy.BuildConfig;

public class APIUtils {

    public final static String API_KEY = BuildConfig.API_KEY;

    public final static String BASE_URL = "https://api.themoviedb.org/3/";

    public final static String CONFIGURATION = "configuration";

    public final static String SEARCH_MOVIES = "search/movie/";

    public final static String MOVIE = "movie/";

    public final static String NOW_PLAYING = "now_playing";

    public final static String UPCOMING = "upcoming";

    public final static String TOP_RATED = "top_rated";

    public final static String POPULAR = "popular";

    public final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    //  "w92", "w154", "w185", "w342", "w500", "w780", "original"
    public final static String POSTER_IMAGE_SIZE = "w342";

    // "w300", "w780", "w1280", "original"
    public final static String BACKDROP_IMAGE_SIZE = "original";

    //  "w45", "w185", "h632", "original"
    //Profile size images
}
