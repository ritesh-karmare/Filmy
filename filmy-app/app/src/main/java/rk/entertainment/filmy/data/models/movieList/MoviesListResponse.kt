package rk.entertainment.filmy.data.models.movieList

import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
        @SerializedName("results")
        var results: List<MoviesListData>,

        @SerializedName("page")
        var page: Int,

        @SerializedName("total_results")
        var totalResults: Int,

        @SerializedName("total_pages")
        var totalPages: Int
)