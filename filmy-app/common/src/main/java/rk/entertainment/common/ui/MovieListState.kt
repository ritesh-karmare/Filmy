package rk.entertainment.common.ui

import rk.entertainment.common.models.movieList.MoviesListResponse

data class MovieListState(
    val movieDetails: MoviesListResponse? = null,
    val loading: Boolean = false,
    val error: String? = null,
)
