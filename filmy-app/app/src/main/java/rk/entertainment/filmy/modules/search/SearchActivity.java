package rk.entertainment.filmy.modules.search;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import rk.entertainment.filmy.R;
import rk.entertainment.filmy.data.models.movieList.MoviesListData;
import rk.entertainment.filmy.modules.movies.MoviesAdapter;
import rk.entertainment.filmy.utils.EndlessRecyclerViewOnScrollListener;
import rk.entertainment.filmy.utils.GridSpacingItemDecoration;
import rk.entertainment.filmy.utils.Utility;
import timber.log.Timber;

public class SearchActivity extends AppCompatActivity implements SearchContract.View, TextWatcher, View.OnTouchListener {

    @BindView(R.id.cl_search)
    CoordinatorLayout clSearch;

    @BindView(R.id.tb_search)
    Toolbar tbSearch;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.rv_search)
    RecyclerView rv_search;

    SearchContract.Presenter presenter;
    GridLayoutManager mGridLayoutManager;
    EndlessRecyclerViewOnScrollListener endlessRecyclerViewOnScrollListener;
    BehaviorSubject<String> publishSubject;
    CompositeDisposable mCompositeDisposable;

    private MoviesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            ButterKnife.bind(this);
            initToolbar();
            initReferences();
            initListeners();
            initRxSubject();
            initPresenter();
        }catch (Exception e) {
            Timber.e(Utility.getExceptionStrign(e));
        }
    }

    private void initToolbar() {
        setSupportActionBar(tbSearch);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initReferences() {

        mCompositeDisposable = new CompositeDisposable();
        publishSubject = BehaviorSubject.create();


        mGridLayoutManager = new GridLayoutManager(this, 2);
        rv_search.setLayoutManager(mGridLayoutManager);
        rv_search.addItemDecoration(new GridSpacingItemDecoration(2, Utility.dpToPx(8, this), true));

        rv_search.setItemAnimator(new DefaultItemAnimator());

        adapter = new MoviesAdapter(this);
        rv_search.setAdapter(adapter);
    }

    private void initListeners() {
        etSearch.addTextChangedListener(this);
        etSearch.setOnTouchListener(this);

        rv_search.addOnScrollListener(endlessRecyclerViewOnScrollListener = new EndlessRecyclerViewOnScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadMore() {
                toggleListenerLoading(true);
                getMovies(true, null);
            }
        });
    }

    private void initRxSubject() {
        mCompositeDisposable.add(publishSubject.debounce(1000, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .filter(s -> (!s.isEmpty()))
                .subscribe(this::onQuery));
    }

    private void onQuery(String s) {
        getMovies(false, s);
    }

    private void initPresenter() {
        presenter = new SearchMoviesPresenter(this);
    }


    private void getMovies(boolean isScrolled, String query) {
        if (Utility.isNetworkAvailable(this)) {
            if (isScrolled)
                presenter.getScrolledResult();
            else
                presenter.getSearchResult(query);
        } else
            showSnackBar(getString(R.string.no_internet_connection));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayMoviesList(List<MoviesListData> upcomingMoviesList) {
        resetAdapter();
        loadAdapter(upcomingMoviesList);
    }

    private void resetAdapter() {
        if (adapter.getItemCount() > 0)
            adapter.clear();
    }

    @Override
    public void displayMoreMoviesList(List<MoviesListData> upcomingMoviesList) {
        toggleListenerLoading(false);
        loadAdapter(upcomingMoviesList);
        rv_search.stopScroll();
    }

    @Override
    public void errorMsg() {
        toggleListenerLoading(false);
        showSnackBar(getString(R.string.err_something_went_wrong));
    }

    private void loadAdapter(List<MoviesListData> upcomingMoviesList) {
        adapter.addAll(upcomingMoviesList);
    }

    private void toggleListenerLoading(boolean isLoading) {
        endlessRecyclerViewOnScrollListener.setLoading(isLoading);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Timber.i("OnTextChanged : %s", charSequence.toString());
        publishSubject.onNext(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (motionEvent.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[2].getBounds().width())) {
                etSearch.setText("");
                return true;
            }
        }

        return false;
    }

    private void showSnackBar(String message) {
        Snackbar.make(clSearch, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (presenter != null)
            presenter.unbind();

        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }
}
