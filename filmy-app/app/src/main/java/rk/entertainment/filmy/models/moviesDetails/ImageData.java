package rk.entertainment.filmy.models.moviesDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageData {

    @SerializedName("backdrops")
    private List<BackdropData> backdrops = null;

    @SerializedName("posters")
    private List<PosterData> posters = null;

    public List<BackdropData> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<BackdropData> backdrops) {
        this.backdrops = backdrops;
    }

    public List<PosterData> getPosters() {
        return posters;
    }

    public void setPosters(List<PosterData> posters) {
        this.posters = posters;
    }

}
