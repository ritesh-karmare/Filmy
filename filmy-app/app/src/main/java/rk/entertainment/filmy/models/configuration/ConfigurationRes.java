package rk.entertainment.filmy.models.configuration;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfigurationRes {

    @SerializedName("images")
    private ConfigImageData images;

    @SerializedName("change_keys")
    private List<String> changeKeys = null;

    public ConfigImageData getConfigImageData() {
        return images;
    }

    public void setConfigImageData(ConfigImageData images) {
        this.images = images;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }

}
