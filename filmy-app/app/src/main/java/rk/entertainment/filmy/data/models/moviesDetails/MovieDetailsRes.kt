package rk.entertainment.filmy.data.models.moviesDetails

import com.google.gson.annotations.SerializedName

data class MovieDetailsRes(

        @SerializedName("adult")
        var adult: Boolean,

        @SerializedName("backdrop_path")
        var backdropPath: String,

        @SerializedName("budget")
        var budget: Int = 0,

        @SerializedName("genres")
        var genres: List<GenreData>,

        @SerializedName("homepage")
        var homepage: String,

        @SerializedName("id")
        var id: Int = 0,

        @SerializedName("imdb_id")
        var imdbId: String,

        @SerializedName("original_language")
        var originalLanguage: String,

        @SerializedName("original_title")
        var originalTitle: String,

        @SerializedName("overview")
        var overview: String,

        @SerializedName("popularity")
        var popularity: Double,

        @SerializedName("poster_path")
        var posterPath: String,

        @SerializedName("production_companies")
        var productionCompanies: List<ProductionCompanyData>,


        @SerializedName("release_date")
        var releaseDate: String,

        @SerializedName("revenue")
        var revenue: Int = 0,

        @SerializedName("runtime")
        var runtime: Int = 0,


        @SerializedName("status")
        var status: String,

        @SerializedName("tagline")
        var tagline: String,

        @SerializedName("title")
        var title: String,

        @SerializedName("video")
        var video: Boolean,

        @SerializedName("vote_average")
        var voteAverage: Double,

        @SerializedName("vote_count")
        var voteCount: Int = 0,

        @SerializedName("videos")
        var videos: VideosList,

        @SerializedName("images")
        var images: ImageData,

        @SerializedName("recommendations")
        var recommendations: RecommendationsList
)