package rk.entertainment.filmy.data.models.moviesDetails

import com.google.gson.annotations.SerializedName

data class BackdropData(

        @SerializedName("file_path")
        var filePath: String = "",
)