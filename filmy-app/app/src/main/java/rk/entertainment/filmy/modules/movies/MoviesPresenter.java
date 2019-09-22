package rk.entertainment.filmy.modules.movies;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rk.entertainment.filmy.models.movieList.MoviesListResponse;
import rk.entertainment.filmy.network.APIClient;
import rk.entertainment.filmy.network.APIService;
import rk.entertainment.filmy.network.APIUtils;
import rk.entertainment.filmy.utils.AppLog;
import rk.entertainment.filmy.utils.MovieModuleTypes;

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
                apiImpl.getNowPlayingMovies(APIUtils.API_KEY, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(observer);
                break;

            case UPCOMING:
                apiImpl.getUpcomingMovies(APIUtils.API_KEY, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(observer);
                break;

            case TOP_RATED:
                apiImpl.getTopRatedMovies(APIUtils.API_KEY, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(observer);
                break;

            case POPULAR:
                apiImpl.getPopularMovies(APIUtils.API_KEY, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(observer);
                break;
        }
    }

    private Observer<MoviesListResponse> observer = new Observer<MoviesListResponse>() {
        @Override
        public void onSubscribe(Disposable d) {
            mCompositeDisposable.add(d);
        }

        @Override
        public void onNext(MoviesListResponse moviesListResponse) {
            handleResponse(moviesListResponse);
        }

        @Override
        public void onError(@NotNull Throwable e) {
            handleError(e);
        }

        @Override
        public void onComplete() {

        }
    };

    // Handle success response
    private void handleResponse(MoviesListResponse moviesListResponse) {
        AppLog.i(MoviesFragment.class.getName(), " Inside onSuccessRes");
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
        AppLog.e(throwable);
        AppLog.i(MoviesFragment.class.getName(), " Inside errorMsg presenter " + throwable);
        handlePageOffset(false);
        if (viewCallback == null) return;
        viewCallback.errorMsg();
    }

    // Increment/Decrement page offset for pagination
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
