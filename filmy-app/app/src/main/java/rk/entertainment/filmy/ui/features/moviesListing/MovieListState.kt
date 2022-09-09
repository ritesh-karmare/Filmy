package rk.entertainment.filmy.ui.features.moviesListing

import rk.entertainment.filmy.data.models.movieList.MoviesListResponse

data class MovieListState(
    val movieDetails: MoviesListResponse? = null,
    val loading: Boolean = false,
    val error: String? = null,
)
