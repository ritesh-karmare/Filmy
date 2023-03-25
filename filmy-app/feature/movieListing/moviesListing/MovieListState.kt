package rk.entertainment.base.ui.features.moviesListing

import rk.entertainment.base.data.models.movieList.MoviesListResponse

data class MovieListState(
    val movieDetails: MoviesListResponse? = null,
    val loading: Boolean = false,
    val error: String? = null,
)
