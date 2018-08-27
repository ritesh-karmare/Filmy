package rk.entertainment.filmy.modules.movies;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import rk.entertainment.filmy.data.models.movieList.MoviesListResponse;
import rk.entertainment.filmy.data.network.APIClient;
import rk.entertainment.filmy.data.network.APIService;
import rk.entertainment.filmy.data.network.APIUtils;
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


    public MoviesPresenter(MoviesContract.View viewCallback) {
        this.viewCallback = viewCallback;
        mCompositeDisposable = new CompositeDisposable();
        page = 0;
        totalPages = 1;
    }


    @Override
    public void getMoviesList(@MovieModuleTypes.MovieModule int moduleType) {

        Timber.i("Page 1 : " + page + " " + totalPages);
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

    private void handleResponse(MoviesListResponse moviesListResponse) {

        if (viewCallback == null)
            return;

        if (moviesListResponse != null) {
            totalPages = moviesListResponse.getTotalPages();
            if (moviesListResponse.getResults() != null && moviesListResponse.getResults().size() > 0)
                if (page == 1)
                    viewCallback.displayMoviesList(moviesListResponse.getResults());
                else
                    viewCallback.displayMoreMoviesList(moviesListResponse.getResults());
        }
    }

    private void handleError(Throwable throwable) {

        try {
            Timber.e(throwable);
            handlePageOffset(false);
            if (viewCallback == null)
                return;
            viewCallback.errorMsg();

        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void handlePageOffset(boolean increment) {
        if (increment) {
            if (page < totalPages)
                page = page + 1;
        } else {
            if (page > 1)
                page = page - 1;
        }
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
