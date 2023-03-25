package rk.entertainment.movie_details

import rk.entertainment.common.models.moviesDetails.MovieDetailsRes

data class MovieDetailsState(
    val movieDetails: MovieDetailsRes? = null,
    val loading: Boolean = false,
    val error: String? = null,
)
