package rk.entertainment.filmy.data.models.moviesDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import rk.entertainment.filmy.data.models.movieList.MoviesListData;

public class RecommendationsList {

    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    private List<MoviesListData> results = null;

    @SerializedName("total_pages")
    private Integer totalPages;

    @SerializedName("total_results")
    private Integer totalResults;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<MoviesListData> getResults() {
        return results;
    }

    public void setResults(List<MoviesListData> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

}
