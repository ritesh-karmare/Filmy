package rk.entertainment.filmy.data.models.moviesDetails

import com.google.gson.annotations.SerializedName

data class PosterData(
        @SerializedName("aspect_ratio")
        var aspectRatio: Double,

        @SerializedName("file_path")
        var filePath: String,

        @SerializedName("height")
        var height: Int,

        @SerializedName("iso_639_1")
        var iso6391: Any,

        @SerializedName("vote_average")
        var voteAverage: Double,

        @SerializedName("vote_count")
        var voteCount: Int,

        @SerializedName("width")
        var width: Int
)