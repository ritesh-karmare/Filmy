package rk.entertainment.filmy.modules.movies;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rk.entertainment.filmy.R;
import rk.entertainment.filmy.models.movieList.MoviesListData;
import rk.entertainment.filmy.utils.AppLog;
import rk.entertainment.filmy.utils.rvUtils.EndlessRecyclerViewOnScrollListener;
import rk.entertainment.filmy.utils.rvUtils.GridSpacingItemDecoration;
import rk.entertainment.filmy.utils.MovieModuleTypes;
import rk.entertainment.filmy.utils.UIUtils;

public class MoviesFragment extends Fragment implements MoviesContract.View {

    @BindView(R.id.swipe_refresh_upcoming_movies)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rv_upcoming_movies)
    RecyclerView recyclerView;

    private @MovieModuleTypes.MovieModule
    int movieModuleType = MovieModuleTypes.UPCOMING;

    private MoviesAdapter adapter;
    private MoviesPresenter presenter;
    private GridLayoutManager mGridLayoutManager;
    private EndlessRecyclerViewOnScrollListener endlessRecyclerViewOnScrollListener;
    private Context context;
    private static final String ARG_KEY = "moviesModuleType";

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
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,
                UIUtils.dpToPx(8, context), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MoviesAdapter(context);
        recyclerView.setAdapter(adapter);
    }

    private void initListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.clear();
            getMovies(true);
        });

        recyclerView.addOnScrollListener(endlessRecyclerViewOnScrollListener =
                new EndlessRecyclerViewOnScrollListener(mGridLayoutManager) {
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

    // Trigger presenter for get the list of movies for specific MovieModuleType
    private void getMovies(boolean isRefreshed) {
        if (UIUtils.isNetworkAvailable(context)) {
            if (isRefreshed)
                presenter.resetPage();

            swipeRefreshLayout.setRefreshing(true);
            presenter.getMoviesList(movieModuleType);
        } else
            UIUtils.displayMessage(context, true, getString(R.string.no_internet_connection)
                    , swipeRefreshLayout, true);
    }

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

    // Handle API error
    @Override
    public void errorMsg() {
        dismissRefreshing();
        toggleListenerLoading(false);
        UIUtils.displayMessage(context, true, getString(R.string.err_something_went_wrong)
                , swipeRefreshLayout, true);
    }

    // Load the adapter with movie list
    private void loadAdapter(List<MoviesListData> upcomingMoviesList) {
        adapter.addAll(upcomingMoviesList);
    }

    // Dismiss swipe refresh
    void dismissRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    // Set the ScrollListener to true\false when end of recyclerview reached for loading more data
    private void toggleListenerLoading(boolean isLoading) {
        endlessRecyclerViewOnScrollListener.setLoading(isLoading);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unbind();
    }
}