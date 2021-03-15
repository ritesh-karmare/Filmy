package rk.entertainment.filmy.data.models.configuration

import com.google.gson.annotations.SerializedName

data class ConfigImageData(
        @SerializedName("base_url")
        var baseUrl: String? = null,

        @SerializedName("secure_base_url")
        var secureBaseUrl: String? = null,

        @SerializedName("backdrop_sizes")
        var backdropSizes: List<String>? = null,

        @SerializedName("logo_sizes")
        var logoSizes: List<String>? = null,

        @SerializedName("poster_sizes")
        var posterSizes: List<String>? = null,

        @SerializedName("profile_sizes")
        var profileSizes: List<String>? = null,

        @SerializedName("still_sizes")
        var stillSizes: List<String>? = null
)