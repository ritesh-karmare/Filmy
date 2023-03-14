package rk.entertainment.filmy.data.network

import rk.entertainment.filmy.BuildConfig

const val API_KEY = BuildConfig.API_KEY
const val BASE_URL = "https://api.themoviedb.org/3/"

const val ENDPOINT_SEARCH_MOVIES = "search/movie"

const val ENDPOINT_MOVIE = "movie/"
const val ENDPOINT_NOW_PLAYING = "now_playing"
const val ENDPOINT_UPCOMING = "upcoming"
const val ENDPOINT_TOP_RATED = "top_rated"
const val ENDPOINT_POPULAR = "popular"

const val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/"
const val DETAIL_TYPES = "images,videos,recommendations"

//  "w92", "w154", "w185", "w342", "w500", "w780", "original"
const val POSTER_IMAGE_SIZE = "w342"

// "w300", "w780", "w1280", "original"
const val BACKDROP_IMAGE_SIZE = "w780" //  "w45", "w185", "h632", "original"
//Profile size images