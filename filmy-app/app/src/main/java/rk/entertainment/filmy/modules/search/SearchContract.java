package rk.entertainment.filmy.modules.search;

import rk.entertainment.filmy.modules.movies.MoviesContract;
import rk.entertainment.filmy.utils.baseContract.BasePresenter;

public interface SearchContract {

    interface View extends MoviesContract.View {
    }

    interface Presenter extends BasePresenter {
        void getSearchResult(String query);

        void getScrolledResult();

        void resetPage();
    }

}
