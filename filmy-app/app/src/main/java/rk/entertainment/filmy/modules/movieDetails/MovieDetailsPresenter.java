package rk.entertainment.filmy.modules.movieDetails;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rk.entertainment.filmy.models.moviesDetails.MovieDetailsRes;
import rk.entertainment.filmy.network.APIClient;
import rk.entertainment.filmy.network.APIService;
import rk.entertainment.filmy.network.APIUtils;
import rk.entertainment.filmy.utils.AppLog;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private MovieDetailsContract.View viewCallback;
    private CompositeDisposable mCompositeDisposable;
    private int movieId;
    private String detailTypes;

    MovieDetailsPresenter(MovieDetailsContract.View viewCallback, int movieId) {
        this.viewCallback = viewCallback;
        this.movieId = movieId;
        detailTypes = "images,videos,recommendations";
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void movieDetails() {
        APIService apiImpl = APIClient.getClient().create(APIService.class);
        apiImpl.getMovieDetails(movieId, APIUtils.API_KEY, detailTypes)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    private Observer<MovieDetailsRes> observer = new Observer<MovieDetailsRes>() {
        @Override
        public void onSubscribe(Disposable d) {
            mCompositeDisposable.add(d);
        }

        @Override
        public void onNext(MovieDetailsRes movieDetailsRes) {
            handleResponse(movieDetailsRes);
        }

        @Override
        public void onError(@NotNull Throwable e) {
            handleError(e);
        }

        @Override
        public void onComplete() {

        }
    };

    private void handleResponse(MovieDetailsRes moviesListResponse) {
        if (viewCallback == null) return;
        if (moviesListResponse != null) viewCallback.movieDetails(moviesListResponse);
    }

    private void handleError(Throwable throwable) {
        AppLog.i(MovieDetailsPresenter.class.getName(), "handleError: "+throwable);
        AppLog.e(throwable);
        if (viewCallback == null) return;
        viewCallback.errorMsg();
    }

    @Override
    public void unbind() {
        viewCallback = null;
        mCompositeDisposable.clear();
    }
}
