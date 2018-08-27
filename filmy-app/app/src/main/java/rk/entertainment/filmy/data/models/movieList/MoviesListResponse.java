package rk.entertainment.filmy.data.models.movieList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesListResponse {

    @SerializedName("results")
    private List<MoviesListData> results = null;

    @SerializedName("page")
    private Integer page;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;


    public List<MoviesListData> getResults() {
        return results;
    }

    public void setResults(List<MoviesListData> results) {
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }


}
