package rk.entertainment.filmy.data.models.moviesDetails

import com.google.gson.annotations.SerializedName

data class GenreData(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String
)