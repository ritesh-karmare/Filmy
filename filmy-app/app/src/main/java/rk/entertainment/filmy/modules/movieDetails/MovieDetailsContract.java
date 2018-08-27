package rk.entertainment.filmy.modules.movieDetails;

import rk.entertainment.filmy.utils.baseContract.BasePresenter;
import rk.entertainment.filmy.utils.baseContract.BaseView;
import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes;

public interface MovieDetailsContract {

    interface View extends BaseView {

        void movieDetails(MovieDetailsRes movieData);

        /*void displayBackdropImageList(ArrayList<BackdropData> backdropDataList);

        void displayTrailerList(List<VideosData> trailerList);

        void displayRecommendedMovieList(List<RecommendationData> recommendationDataList);

        void displayMovieData();

        void genre();
        */

    }

    interface Presenter extends BasePresenter {

        void movieDetails();


    }

}
