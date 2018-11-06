package rk.entertainment.filmy.modules.search;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import rk.entertainment.filmy.models.movieList.MoviesListResponse;
import rk.entertainment.filmy.network.APIClient;
import rk.entertainment.filmy.network.APIService;
import rk.entertainment.filmy.network.APIUtils;
import timber.log.Timber;

public class SearchMoviesPresenter implements SearchContract.Presenter {

    private SearchContract.View viewCallback;
    private CompositeDisposable mCompositeDisposable;
    private int page;
    private int totalPages;
    private String query;


    SearchMoviesPresenter(SearchContract.View viewCallback) {
        this.viewCallback = viewCallback;
        mCompositeDisposable = new CompositeDisposable();
        page = 0;
        totalPages = 1;
        query = "";
    }

    @Override
    public void getSearchResult(String query) {
        this.query = query;
        getResult(true);
    }

    @Override
    public void getScrolledResult() {
        getResult(false);
    }


    private void getResult(boolean resetPage) {
        if (resetPage) resetPage();

        handlePageOffset(true);
        APIService apiImpl = APIClient.getClient().create(APIService.class);
        mCompositeDisposable.add(apiImpl.getSearchedMovies(APIUtils.API_KEY, query, false, page)
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .debounce(5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(MoviesListResponse moviesListResponse) {
        if (viewCallback == null) return;
        if (moviesListResponse != null) {
            totalPages = moviesListResponse.getTotalPages();
            if (moviesListResponse.getResults() != null && moviesListResponse.getResults().size() > 0)
                if (page == 1) viewCallback.displayMoviesList(moviesListResponse.getResults());
                else viewCallback.displayMoreMoviesList(moviesListResponse.getResults());
        }
    }

    private void handleError(Throwable throwable) {
        Timber.e(throwable);
        handlePageOffset(false);
        if (viewCallback == null) return;
        viewCallback.errorMsg();
    }

    private void handlePageOffset(boolean increment) {
        if (increment) {
            if (page < totalPages) page = page + 1;
        } else if (page > 1) page = page - 1;
    }

    @Override
    public void resetPage() {
        page = 0;
    }

    @Override
    public void unbind() {
        viewCallback = null;
        mCompositeDisposable.clear();
    }
}
