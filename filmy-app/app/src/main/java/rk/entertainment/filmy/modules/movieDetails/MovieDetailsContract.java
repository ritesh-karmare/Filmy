package rk.entertainment.filmy.modules.movieDetails;

import rk.entertainment.filmy.models.moviesDetails.MovieDetailsRes;
import rk.entertainment.filmy.utils.baseContract.BasePresenter;
import rk.entertainment.filmy.utils.baseContract.BaseView;

public interface MovieDetailsContract {

    interface View extends BaseView {
        void movieDetails(MovieDetailsRes movieData);
    }

    interface Presenter extends BasePresenter {
        void movieDetails();
    }

}
