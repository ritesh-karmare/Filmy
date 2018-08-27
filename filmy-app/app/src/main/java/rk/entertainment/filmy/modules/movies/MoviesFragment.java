package rk.entertainment.filmy.modules.movies;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rk.entertainment.filmy.R;
import rk.entertainment.filmy.data.models.movieList.MoviesListData;
import rk.entertainment.filmy.utils.MovieModuleTypes;
import rk.entertainment.filmy.utils.EndlessRecyclerViewOnScrollListener;
import rk.entertainment.filmy.utils.GridSpacingItemDecoration;
import rk.entertainment.filmy.utils.Utility;


public class MoviesFragment extends Fragment implements MoviesContract.View {

    @BindView(R.id.swipe_refresh_upcoming_movies)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rv_upcoming_movies)
    RecyclerView recyclerView;

    private MoviesAdapter adapter;
    private MoviesPresenter presenter;

    private @MovieModuleTypes.MovieModule
    int movieModuleType = MovieModuleTypes.UPCOMING;

    private static final String ARG_KEY = "moviesModuleType";

    GridLayoutManager mGridLayoutManager;
    EndlessRecyclerViewOnScrollListener endlessRecyclerViewOnScrollListener;

    public static MoviesFragment newInstance(@MovieModuleTypes.MovieModule int movieModuleType) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_KEY, movieModuleType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            movieModuleType = getArguments().getInt(ARG_KEY);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_list, container, false);
        ButterKnife.bind(this, rootView);
        initReferences();
        initListeners();
        initPresenter();
        return rootView;
    }

    private void initReferences() {
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utility.dpToPx(8, getContext()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MoviesAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void initListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.clear();
            getMovies(true);
        });

        recyclerView.addOnScrollListener(endlessRecyclerViewOnScrollListener = new EndlessRecyclerViewOnScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadMore() {
                toggleListenerLoading(true);
                getMovies(false);
            }
        });
    }

    private void initPresenter() {
        presenter = new MoviesPresenter(this);
        getMovies(false);
    }


    private void getMovies(boolean isRefreshed) {
        if (Utility.isNetworkAvailable(getContext())) {
            if (isRefreshed)
                presenter.resetPage();

            swipeRefreshLayout.setRefreshing(true);
            presenter.getMoviesList(movieModuleType);
        } else
            showSnackBar(getString(R.string.no_internet_connection));
    }

     /*
    Movies callbacks
     */

    @Override
    public void displayMoviesList(List<MoviesListData> upcomingMoviesList) {
        dismissRefreshing();
        loadAdapter(upcomingMoviesList);
    }

    @Override
    public void displayMoreMoviesList(List<MoviesListData> upcomingMoviesList) {
        dismissRefreshing();
        toggleListenerLoading(false);
        loadAdapter(upcomingMoviesList);
        recyclerView.stopScroll();
    }

    @Override
    public void errorMsg() {
        dismissRefreshing();
        toggleListenerLoading(false);
        showSnackBar(getString(R.string.err_something_went_wrong));
    }

    private void loadAdapter(List<MoviesListData> upcomingMoviesList) {
        adapter.addAll(upcomingMoviesList);
    }

    void dismissRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void toggleListenerLoading(boolean isLoading) {
        endlessRecyclerViewOnScrollListener.setLoading(isLoading);
    }

    private void showSnackBar(String string) {
        Snackbar.make(swipeRefreshLayout, string, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unbind();
    }

}