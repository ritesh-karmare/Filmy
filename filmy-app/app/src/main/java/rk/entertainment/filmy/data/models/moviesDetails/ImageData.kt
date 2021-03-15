package rk.entertainment.filmy.data.models.moviesDetails

import com.google.gson.annotations.SerializedName

data class ImageData(
        @SerializedName("backdrops")
        var backdrops: List<BackdropData>,

        @SerializedName("posters")
        var posters: List<PosterData>
)