package rk.entertainment.filmy.modules.movieDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rk.entertainment.filmy.R;
import rk.entertainment.filmy.models.movieList.MoviesListData;
import rk.entertainment.filmy.models.moviesDetails.BackdropData;
import rk.entertainment.filmy.models.moviesDetails.GenreData;
import rk.entertainment.filmy.models.moviesDetails.MovieDetailsRes;
import rk.entertainment.filmy.models.moviesDetails.ProductionCompanyData;
import rk.entertainment.filmy.models.moviesDetails.VideosData;
import rk.entertainment.filmy.modules.movies.MoviesAdapter;
import rk.entertainment.filmy.utils.AppLog;
import rk.entertainment.filmy.utils.DateTimeUtil;
import rk.entertainment.filmy.utils.UIUtils;
import rk.entertainment.filmy.utils.Utility;
import rk.entertainment.filmy.utils.rvUtils.VerticalItemDecoration;

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

    @BindView(R.id.tv_movie_details_videos)
    TextView tvVideoText;

    @BindView(R.id.rv_videos)
    RecyclerView rvVideos;

    @BindView(R.id.tv_movie_details_prod_recommendedtxt)
    TextView tvRecommendedText;

    @BindView(R.id.rv_recommended)
    RecyclerView rvRecommended;

    // appbar scroll range
    private int scrollRange = -1;
    private boolean isShowTitle = true;
    private String title;
    private int movieId;

    private MovieDetailsContract.Presenter movieDetailsPresenter;
    private CustomPagerAdapter customPagerAdapter;
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
            AppLog.e(e);
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

    // Trigger presenter to get the movie details
    private void getMovieDetails() {
        if (UIUtils.isNetworkAvailable(this))
            movieDetailsPresenter.movieDetails();
        else
            UIUtils.displayMessage(this, true,
                    getString(R.string.no_internet_connection), clMovieDetails, true);
    }

    // Init the dots for viewPager
    private void initDots() {
        //init params
        int marginLeftRight = UIUtils.dpToPx(2, this);
        int marginTopBottom = UIUtils.dpToPx(8, this);
        int width = UIUtils.dpToPx(6, this);
        imageParam = new LinearLayout.LayoutParams(width, width);
        imageParam.setMargins(marginLeftRight, marginTopBottom, marginLeftRight, marginTopBottom);
        indicators = new ArrayList<>();
    }

    // Add the dots for viewPager based on image count
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

    // Update the indicator to selected/default state on scroll of viewPager
    private void updateIndicators(int selectedPostion) {
        for (int indicatorPosition = 0; indicatorPosition < indicators.size(); indicatorPosition++) {
            indicators.get(indicatorPosition).setBackgroundResource(
                    indicatorPosition == selectedPostion ? R.drawable.indicator_selected
                            : R.drawable.indicator_default);
        }
    }

    // Movie details main response method
    @Override
    public void movieDetails(MovieDetailsRes movieData) {
        setUpMovieHeader(movieData.getImages().getBackdrops(), movieData.getTitle(),
                movieData.getGenres(), movieData.getReleaseDate(),
                movieData.getVoteAverage().toString(), movieData.getRuntime());

        setUpOverView(movieData.getOverview());

        setUpMovieFacts(movieData.getOriginalTitle(), movieData.getStatus(),
                movieData.getProductionCompanies());

        initVideos(movieData.getVideos().getResults());

        initRecommendedMovies(movieData.getRecommendations().getResults());
    }

    // Set Images, title, genre, releaseDate, VoteAverage, Runtime
    private void setUpMovieHeader(List<BackdropData> backdrops, String title, List<GenreData> genres,
                                  String releaseDate, String rating, int runtime) {

        customPagerAdapter.addAll(backdrops);
        initDots();
        addDots(backdrops.size());

        String genre = Utility.getAppendedStringFromList(genres);
        String releaseYear = DateTimeUtil.getYearFromDate(releaseDate);
        this.title = title;

        tvMovieName.setText(title);
        tvMovieYear.setText(releaseYear);
        tvMovieRating.setText(rating);

        if (genre != null) {
            tvMovieGenre.setVisibility(View.VISIBLE);
            tvMovieGenre.setText(genre);
        } else
            tvMovieGenre.setVisibility(View.GONE);

        tv_movie_details_runtime.setText(DateTimeUtil.getHoursAndMinutes(runtime));

        app_bar.addOnOffsetChangedListener(this);

        tvMovieName.setOnClickListener(view -> startActivity
                (new Intent(MovieDetailsActivity.this, TrailerActivity.class)));
    }

    //  Set Overview (description)
    private void setUpOverView(String overview) {
        tvMovieOverview.setText(overview);
    }

    // Set original title, status, production companies
    private void setUpMovieFacts(String originalTitle, String status,
                                 List<ProductionCompanyData> productionCompanies) {
        tvMovieOriginalTitle.setText(originalTitle);
        tvMovieStatus.setText(status);
        tvProductionCompanies.setText(Utility.getAppendedStringFromList(productionCompanies));
    }

    // Set videos thumbnail in the horizontal recyclerView
    private void initVideos(List<VideosData> videosList) {
        if (videosList.size() > 0) {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

            rvVideos.setLayoutManager(mLayoutManager);
            rvVideos.addItemDecoration(new VerticalItemDecoration(this));

            VideosAdapter videosAdapter = new VideosAdapter(this);
            videosAdapter.addAll(videosList);
            rvVideos.setAdapter(videosAdapter);
        } else {
            tvVideoText.setVisibility(View.GONE);
            rvVideos.setVisibility(View.GONE);
        }
    }

    // Set recommended movies in the horizontal recyclerView
    private void initRecommendedMovies(List<MoviesListData> recommendationsList) {
        if (recommendationsList.size() > 0) {
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

    // Handle API error
    @Override
    public void errorMsg() {
        UIUtils.displayMessage(this, true,
                getString(R.string.err_something_went_wrong), clMovieDetails, true);
    }

    // Handle the collapsible toolbar title
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
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