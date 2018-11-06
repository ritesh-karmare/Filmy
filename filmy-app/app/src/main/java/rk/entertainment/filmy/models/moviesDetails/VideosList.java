package rk.entertainment.filmy.models.moviesDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideosList {

    @SerializedName("results")
    private List<VideosData> results = null;

    public List<VideosData> getResults() {
        return results;
    }

    public void setResults(List<VideosData> results) {
        this.results = results;
    }
}
