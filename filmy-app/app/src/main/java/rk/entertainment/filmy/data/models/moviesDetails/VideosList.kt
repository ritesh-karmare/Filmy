package rk.entertainment.filmy.data.models.moviesDetails

import com.google.gson.annotations.SerializedName

data class VideosList(
        @SerializedName("results")
        var results: List<VideosData>
)