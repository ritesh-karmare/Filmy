package rk.entertainment.common.models.movieList

data class MoviesListResponse(
    var results: List<MoviesListData>,
)

data class MoviesListData(
    var voteCount: Int? = null,
    var id: Int? = null,
    var video: Boolean? = null,
    var voteAverage: Double? = null,
    var title: String? = null,
    var posterPath: String? = null,
    var originalLanguage: String? = null,
    var originalTitle: String? = null,
    var genreIds: List<Int>? = null,
    var backdropPath: String? = null,
    var adult: Boolean? = null,
    var overview: String? = null,
    var releaseDate: String? = null
)