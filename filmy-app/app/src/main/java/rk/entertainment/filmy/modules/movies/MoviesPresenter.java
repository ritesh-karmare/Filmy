package rk.entertainment.filmy.modules.movies;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import rk.entertainment.filmy.models.movieList.MoviesListResponse;
import rk.entertainment.filmy.network.APIClient;
import rk.entertainment.filmy.network.APIService;
import rk.entertainment.filmy.network.APIUtils;
import rk.entertainment.filmy.utils.MovieModuleTypes;
import timber.log.Timber;

import static rk.entertainment.filmy.utils.MovieModuleTypes.NOW_PLAYING;
import static rk.entertainment.filmy.utils.MovieModuleTypes.POPULAR;
import static rk.entertainment.filmy.utils.MovieModuleTypes.TOP_RATED;
import static rk.entertainment.filmy.utils.MovieModuleTypes.UPCOMING;

public class MoviesPresenter implements MoviesContract.Presenter {

    private MoviesContract.View viewCallback;
    private CompositeDisposable mCompositeDisposable;
    private int page;
    private int totalPages;

    MoviesPresenter(MoviesContract.View viewCallback) {
        this.viewCallback = viewCallback;
        mCompositeDisposable = new CompositeDisposable();
        page = 0;
        totalPages = 1;
    }

    @Override
    public void getMoviesList(@MovieModuleTypes.MovieModule int moduleType) {
        handlePageOffset(true);
        APIService apiImpl = APIClient.getClient().create(APIService.class);
        switch (moduleType) {
            case NOW_PLAYING:
                mCompositeDisposable.add(apiImpl.getNowPlayingMovies(APIUtils.API_KEY, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
                break;

            case UPCOMING:
                mCompositeDisposable.add(apiImpl.getUpcomingMovies(APIUtils.API_KEY, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
                break;

            case TOP_RATED:
                mCompositeDisposable.add(apiImpl.getTopRatedMovies(APIUtils.API_KEY, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
                break;

            case POPULAR:
                mCompositeDisposable.add(apiImpl.getPopularMovies(APIUtils.API_KEY, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
                break;
        }
    }

    // Handle success response
    private void handleResponse(MoviesListResponse moviesListResponse) {
        if (viewCallback == null) return;
        if (moviesListResponse != null) {
            totalPages = moviesListResponse.getTotalPages();
            if (moviesListResponse.getResults() != null && moviesListResponse.getResults().size() > 0)
                if (page == 1) viewCallback.displayMoviesList(moviesListResponse.getResults());
                else viewCallback.displayMoreMoviesList(moviesListResponse.getResults());
        }
    }

    // handle error response
    private void handleError(Throwable throwable) {
            Timber.e(throwable);
            handlePageOffset(false);
            if (viewCallback == null) return;
            viewCallback.errorMsg();
    }

    // Increment/Decrement page offset for pagination
    private void handlePageOffset(boolean increment) {
        if (increment) {
            if (page < totalPages) page = page + 1;
        } else
            if (page > 1)  page = page - 1;
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
