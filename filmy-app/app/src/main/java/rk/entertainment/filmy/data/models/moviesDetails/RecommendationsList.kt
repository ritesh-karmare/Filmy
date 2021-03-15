package rk.entertainment.filmy.data.models.moviesDetails

import com.google.gson.annotations.SerializedName
import rk.entertainment.filmy.data.models.movieList.MoviesListData

data class RecommendationsList(
        @SerializedName("page")
        var page: Int,

        @SerializedName("results")
        var results: List<MoviesListData>,

        @SerializedName("total_pages")
        var totalPages: Int,

        @SerializedName("total_results")
        var totalResults: Int
)