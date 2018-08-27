package rk.entertainment.filmy.modules.movieDetails;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rk.entertainment.filmy.R;
import rk.entertainment.filmy.data.models.movieList.MoviesListData;
import rk.entertainment.filmy.data.models.moviesDetails.BackdropData;
import rk.entertainment.filmy.data.models.moviesDetails.GenreData;
import rk.entertainment.filmy.data.models.moviesDetails.MovieDetailsRes;
import rk.entertainment.filmy.data.models.moviesDetails.ProductionCompanyData;
import rk.entertainment.filmy.modules.movies.MoviesAdapter;
import rk.entertainment.filmy.utils.Utility;
import rk.entertainment.filmy.utils.VerticalItemDecoration;
import timber.log.Timber;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.cl_movie_details)
    CoordinatorLayout clMovieDetails;

    @BindView(R.id.app_bar)
    AppBarLayout app_bar;

    @BindView(R.id.collapsable_toolbar)
    CollapsingToolbarLayout collapsable_toolbar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.linlay_indicator)
    LinearLayout linlay_indicator;

    @BindView(R.id.tv_movie_details_name)
    TextView tvMovieName;

    @BindView(R.id.tv_movie_genre)
    TextView tvMovieGenre;

    @BindView(R.id.tv_movie_details_year)
    TextView tvMovieYear;

    @BindView(R.id.tv_movie_details_runtime)
    TextView tv_movie_details_runtime;

    @BindView(R.id.tv_movie_details_rating)
    TextView tvMovieRating;

    @BindView(R.id.tv_movie_details_overview)
    TextView tvMovieOverview;

    @BindView(R.id.tv_movie_details_org_title)
    TextView tvMovieOriginalTitle;

    @BindView(R.id.tv_movie_details_status)
    TextView tvMovieStatus;

    @BindView(R.id.tv_movie_details_prod_companies)
    TextView tvProductionCompanies;

    @BindView(R.id.tv_movie_details_prod_recommendedtxt)
    TextView tvRecommendedText;

    @BindView(R.id.rv_recommended)
    RecyclerView rvRecommended;

    // appbar scroll range
    int scrollRange = -1;
    boolean isShowTitle = true;

    MovieDetailsContract.Presenter movieDetailsPresenter;
    CustomPagerAdapter customPagerAdapter;

    String title;
    int movieId;

    private LinearLayout.LayoutParams imageParam;
    private List<ImageView> indicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movie_details);
            ButterKnife.bind(this);

            initToolbar();
            initReferences();
            initListeners();
            initPresenter();
            getMovieDetails();
        } catch (Exception e) {
            Timber.e(Utility.getExceptionStrign(e));
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsable_toolbar.setTitle(" ");
    }

    private void initReferences() {
        movieId = getIntent().getIntExtra("movieId", -1);
        customPagerAdapter = new CustomPagerAdapter(this, new ArrayList<>());
        mViewPager.setAdapter(customPagerAdapter);
    }

    private void initListeners() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPresenter() {
        movieDetailsPresenter = new MovieDetailsPresenter(this, movieId);
    }

    private void getMovieDetails() {
        if (Utility.isNetworkAvailable(this))
            movieDetailsPresenter.movieDetails();
        else
            showSnackBar(getString(R.string.no_internet_connection));
    }

    @Override
    public void movieDetails(MovieDetailsRes movieData) {
        setUpMovieHeader(movieData.getImages().getBackdrops(), movieData.getTitle(), movieData.getGenres(), movieData.getReleaseDate(), movieData.getVoteAverage().toString(), movieData.getRuntime());
        setUpOverView(movieData.getOverview());
        setUpMovieFacts(movieData.getOriginalTitle(), movieData.getStatus(), movieData.getProductionCompanies());
        initRecommendedMovies(movieData.getRecommendations().getResults());
    }

    private void setUpMovieHeader(List<BackdropData> backdrops, String title, List<GenreData> genres, String releaseDate, String rating, int runtime) {

        customPagerAdapter.addAll(backdrops);
        initDots();
        addDots(backdrops.size());

        String genre = Utility.getAppendedStringFromList(genres);
        String releaseYear = Utility.getYearFromDate(releaseDate);
        this.title = title;

        tvMovieName.setText(title);
        tvMovieYear.setText(releaseYear);
        tvMovieRating.setText(rating);

        if (genre != null) {
            tvMovieGenre.setVisibility(View.VISIBLE);
            tvMovieGenre.setText(genre);
        } else
            tvMovieGenre.setVisibility(View.GONE);

        tv_movie_details_runtime.setText(Utility.getHoursAndMinutes(runtime));

        app_bar.addOnOffsetChangedListener(this);
    }

    private void setUpOverView(String overview) {
        tvMovieOverview.setText(overview);
    }

    private void setUpMovieFacts(String originalTitle, String status, List<ProductionCompanyData> productionCompanies) {
        tvMovieOriginalTitle.setText(originalTitle);
        tvMovieStatus.setText(status);
        tvProductionCompanies.setText(Utility.getAppendedStringFromList(productionCompanies));
    }

    private void initRecommendedMovies(List<MoviesListData> recommendationsList) {

        if (recommendationsList.size() > 0) {

            ViewCompat.setNestedScrollingEnabled(rvRecommended, false);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

            rvRecommended.setLayoutManager(mLayoutManager);
            rvRecommended.addItemDecoration(new VerticalItemDecoration(this));


            MoviesAdapter moviesAdapter = new MoviesAdapter(this);
            moviesAdapter.addAll(recommendationsList);
            rvRecommended.setAdapter(moviesAdapter);
        } else {
            tvRecommendedText.setVisibility(View.GONE);
            rvRecommended.setVisibility(View.GONE);
        }


    }

    @Override
    public void errorMsg() {
        showSnackBar(getString(R.string.err_something_went_wrong));
    }

    private void initDots() {
        //init params
        int marginLeftRight = Utility.dpToPx(2, this);
        int marginTopBottom = Utility.dpToPx(8, this);
        int width = Utility.dpToPx(6, this);
        imageParam = new LinearLayout.LayoutParams(width, width);
        imageParam.setMargins(marginLeftRight, marginTopBottom, marginLeftRight, marginTopBottom);

        indicators = new ArrayList<>();
    }

    private void addDots(int dotCount) {
        for (int indicatorCount = 0; indicatorCount < dotCount; indicatorCount++) {

            ImageView imageIndicator = new ImageView(this);
            imageIndicator.setAdjustViewBounds(true);
            imageIndicator.setScaleType(ImageView.ScaleType.FIT_XY);
            imageIndicator.setLayoutParams(imageParam);

            linlay_indicator.addView(imageIndicator);
            indicators.add(imageIndicator);
            imageIndicator.setBackgroundResource(R.drawable.indicator_default);
        }
        if (indicators.size() > 0)
            indicators.get(0).setBackgroundResource(R.drawable.indicator_selected);
    }

    private void updateIndicators(int selectedPostion) {
        for (int indicatorPosition = 0; indicatorPosition < indicators.size(); indicatorPosition++) {
            indicators.get(indicatorPosition).setBackgroundResource(indicatorPosition == selectedPostion ? R.drawable.indicator_selected
                    : R.drawable.indicator_default);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1)
            scrollRange = appBarLayout.getTotalScrollRange();

        if (scrollRange + verticalOffset == 0) {
            collapsable_toolbar.setTitle(title);
            isShowTitle = true;
        } else if (isShowTitle) {
            collapsable_toolbar.setTitle(" ");
            isShowTitle = false;
        }
    }

    private void showSnackBar(String message) {
        Snackbar.make(clMovieDetails, message, Snackbar.LENGTH_LONG).show();
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
    protected void onDestroy() {
        super.onDestroy();
        if (movieDetailsPresenter != null)
            movieDetailsPresenter.unbind();
    }
}
