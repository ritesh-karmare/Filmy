package rk.entertainment.filmy.data.models.moviesDetails

import rk.entertainment.filmy.data.models.movieList.MoviesListData

data class MovieDetailsRes(
        var adult: Boolean,
        var genres: List<GenreData>,
        var originalTitle: String,
        var overview: String,
        var productionCompanies: List<ProductionCompanyData>,
        var releaseDate: String,
        var runtime: Int = 0,
        var status: String,
        var title: String,
        var voteAverage: Double,
        var videos: VideosList,
        var images: ImageData,
        var recommendations: RecommendationsList
)

data class GenreData(
        var id: Int,
        var name: String
)

data class ProductionCompanyData(
        var name: String
)

data class RecommendationsList(
        var results: List<MoviesListData>
)

data class VideosData(
        var key: String? = null,
)

data class VideosList(
        var results: List<VideosData>
)

data class ImageData(
        var backdrops: List<BackdropData>,
)

data class BackdropData(
        var filePath: String = "",
)