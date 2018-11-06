package rk.entertainment.filmy.modules.movies;

import java.util.List;

import rk.entertainment.filmy.models.movieList.MoviesListData;
import rk.entertainment.filmy.utils.MovieModuleTypes;
import rk.entertainment.filmy.utils.baseContract.BasePresenter;
import rk.entertainment.filmy.utils.baseContract.BaseView;

public interface MoviesContract {

    interface View extends BaseView {
        void displayMoviesList(List<MoviesListData> upcomingMoviesList);

        void displayMoreMoviesList(List<MoviesListData> upcomingMoviesList);
    }

    interface Presenter extends BasePresenter {
        void getMoviesList(@MovieModuleTypes.MovieModule int movieModule);

        void resetPage();
    }
}
