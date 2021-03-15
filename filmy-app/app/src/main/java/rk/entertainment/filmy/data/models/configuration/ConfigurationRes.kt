package rk.entertainment.filmy.data.models.configuration

import com.google.gson.annotations.SerializedName

data class ConfigurationRes (
    @SerializedName("images")
    var configImageData: ConfigImageData? = null,

    @SerializedName("change_keys")
    var changeKeys: List<String>? = null
)