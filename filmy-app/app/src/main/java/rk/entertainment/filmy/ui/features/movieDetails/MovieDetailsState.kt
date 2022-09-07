package rk.entertainment.filmy.ui.features.movieDetails

import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes

data class MovieDetailsState(
    val movieDetails: MovieDetailsRes? = null,
    val loading: Boolean = false,
    val error: String? = null,
)
