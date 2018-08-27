package rk.entertainment.filmy.modules.movieDetails;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes;
import rk.entertainment.filmy.data.network.APIClient;
import rk.entertainment.filmy.data.network.APIService;
import rk.entertainment.filmy.data.network.APIUtils;
import timber.log.Timber;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private MovieDetailsContract.View viewCallback;
    private CompositeDisposable mCompositeDisposable;

    private int movieId;
    private String detailTypes = null;

    public MovieDetailsPresenter(MovieDetailsContract.View viewCallback, int movieId) {
        this.viewCallback = viewCallback;
        this.movieId = movieId;

        detailTypes = "images,videos,recommendations";
        mCompositeDisposable = new CompositeDisposable();

    }



    @Override
    public void movieDetails() {
        APIService apiImpl = APIClient.getClient().create(APIService.class);
        mCompositeDisposable.add(apiImpl.getMovieDetails(movieId, APIUtils.API_KEY, detailTypes)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(MovieDetailsRes moviesListResponse) {

        if (viewCallback == null)
            return;

        if (moviesListResponse != null) {
            viewCallback.movieDetails(moviesListResponse);
        }
    }

    private void handleError(Throwable throwable) {

        try {

            Timber.e(throwable);

            if (viewCallback == null)
                return;
            viewCallback.errorMsg();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void unbind() {
        viewCallback = null;
        mCompositeDisposable.clear();
    }

}
