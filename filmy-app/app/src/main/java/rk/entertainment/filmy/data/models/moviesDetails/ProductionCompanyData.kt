package rk.entertainment.filmy.data.models.moviesDetails

import com.google.gson.annotations.SerializedName

data class ProductionCompanyData(
        @SerializedName("id")
        var id: Int,

        @SerializedName("logo_path")
        var logoPath: String,

        @SerializedName("name")
        var name: String,

        @SerializedName("origin_country")
        var originCountry: String
)