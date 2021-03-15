package rk.entertainment.filmy.view.features.movieDetails

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import rk.entertainment.filmy.R
import rk.entertainment.filmy.data.models.movieList.MoviesListData
import rk.entertainment.filmy.data.models.moviesDetails.*
import rk.entertainment.filmy.databinding.ActivityMovieDetailsBinding
import rk.entertainment.filmy.utils.DateTimeUtil.getHoursAndMinutes
import rk.entertainment.filmy.utils.DateTimeUtil.getYearFromDate
import rk.entertainment.filmy.utils.UIUtils.displayMessage
import rk.entertainment.filmy.utils.UIUtils.dpToPx
import rk.entertainment.filmy.utils.UIUtils.isNetworkAvailable
import rk.entertainment.filmy.utils.rvUtils.VerticalItemDecoration
import rk.entertainment.filmy.view.features.moviesListing.MoviesListingAdapter
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class MovieDetailsActivity : AppCompatActivity(), OnOffsetChangedListener {
    // appbar scroll range
    private var scrollRange = -1
    private var isShowTitle = true
    private var title: String? = null
    private var movieId: Int = 0
    private var customPagerAdapter: CustomPagerAdapter? = null
    private var imageParam: LinearLayout.LayoutParams? = null
    private var indicators: ArrayList<ImageView>? = null

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
            setContentView(binding.root)
            initToolbar()
            initReferences()
            initListeners()
            initViewModel()
            getMovieDetails()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        viewModel.errorListener.observe(this, { errMsg: String -> errorMsg(errMsg) })
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.collapsableToolbar.title = " "
    }

    private fun initReferences() {
        movieId = intent.getIntExtra("movieId", -1)
        customPagerAdapter = CustomPagerAdapter(this, ArrayList())
        binding.viewPager.adapter = customPagerAdapter
    }

    private fun initListeners() {
        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                updateIndicators(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    // Trigger presenter to get the movie details
    private fun getMovieDetails() {
        if (isNetworkAvailable(this))
            viewModel.getMovieDetails(movieId).observe(this, { movieData: MovieDetailsRes -> movieDetails(movieData) })
        else
            displayMessage(this, true, getString(R.string.no_internet_connection), binding.clMovieDetails, true)
    }

    // Init the dots for viewPager
    private fun initDots() {
        //init params
        val marginLeftRight = dpToPx(2f, this)
        val marginTopBottom = dpToPx(8f, this)
        val width = dpToPx(6f, this)
        imageParam = LinearLayout.LayoutParams(width, width)
        imageParam?.setMargins(marginLeftRight, marginTopBottom, marginLeftRight, marginTopBottom)
        indicators = ArrayList()
    }

    // Add the dots for viewPager based on image count
    private fun addDots(dotCount: Int) {
        for (indicatorCount in 0 until dotCount) {
            val imageIndicator = ImageView(this)
            imageIndicator.adjustViewBounds = true
            imageIndicator.scaleType = ImageView.ScaleType.FIT_XY
            imageIndicator.layoutParams = imageParam
            binding.linlayIndicator.addView(imageIndicator)
            indicators?.add(imageIndicator)
            imageIndicator.setBackgroundResource(R.drawable.indicator_default)
        }
        if (indicators!!.size > 0) indicators!![0].setBackgroundResource(R.drawable.indicator_selected)
    }

    // Update the indicator to selected/default state on scroll of viewPager
    private fun updateIndicators(selectedPostion: Int) {
        for (indicatorPosition in indicators!!.indices) {
            indicators!![indicatorPosition].setBackgroundResource(
                    if (indicatorPosition == selectedPostion)
                        R.drawable.indicator_selected
                    else R.drawable.indicator_default)
        }
    }

    // Movie details main response method
    fun movieDetails(movieData: MovieDetailsRes) {
        setUpMovieHeader(movieData.images.backdrops, movieData.title, movieData.genres, movieData.releaseDate, movieData.voteAverage.toString(), movieData.runtime)
        setUpOverView(movieData.overview)
        setUpMovieFacts(movieData.originalTitle, movieData.status, movieData.productionCompanies)
        initVideos(movieData.videos.results)
        initRecommendedMovies(movieData.recommendations.results)
    }

    // Set Images, title, genre, releaseDate, VoteAverage, Runtime
    private fun setUpMovieHeader(backdrops: List<BackdropData>, title: String, genres: List<GenreData>,
                                 releaseDate: String, rating: String, runtime: Int) {
        customPagerAdapter?.addAll(backdrops)
        initDots()
        addDots(backdrops.size)
        val releaseYear = getYearFromDate(releaseDate)
        this.title = title
        binding.tvMovieDetailsName.text = title
        binding.tvMovieDetailsYear.text = releaseYear
        binding.tvMovieDetailsRating.text = rating

        if (!genres.isNullOrEmpty()) {
            val genre = genres.joinToString(",") { it.name } //getAppendedStringFromList(genres)
            binding.tvMovieGenre.visibility = View.VISIBLE
            binding.tvMovieGenre.text = genre
        } else
            binding.tvMovieGenre.visibility = View.GONE

        binding.tvMovieDetailsRuntime.text = getHoursAndMinutes(runtime)
        binding.appBar.addOnOffsetChangedListener(this)
        binding.tvMovieDetailsName.setOnClickListener {
            startActivity(Intent(this@MovieDetailsActivity, TrailerActivity::class.java))
        }
    }

    //  Set Overview (description)
    private fun setUpOverView(overview: String) {
        binding.tvMovieDetailsOverview.text = overview
    }

    // Set original title, status, production companies
    private fun setUpMovieFacts(originalTitle: String, status: String,
                                productionCompanies: List<ProductionCompanyData>) {
        binding.tvMovieDetailsOrgTitle.text = originalTitle
        binding.tvMovieDetailsStatus.text = status
        binding.tvMovieDetailsProdCompanies.text = productionCompanies.joinToString(",") { it.name } //getAppendedStringFromList(productionCompanies)
    }

    // Set videos thumbnail in the horizontal recyclerView
    private fun initVideos(videosList: List<VideosData>) {
        if (videosList.isNotEmpty()) {
            val mLayoutManager = LinearLayoutManager(this)
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.rvVideos.layoutManager = mLayoutManager
            binding.rvVideos.addItemDecoration(VerticalItemDecoration(this))
            val videosAdapter = VideosAdapter(this)
            videosAdapter.addAll(videosList)
            binding.rvVideos.adapter = videosAdapter
        } else {
            binding.tvMovieDetailsVideos.visibility = View.GONE
            binding.rvVideos.visibility = View.GONE
        }
    }

    // Set recommended movies in the horizontal recyclerView
    private fun initRecommendedMovies(recommendationsList: List<MoviesListData>) {
        if (recommendationsList.isNotEmpty()) {
            val mLayoutManager = LinearLayoutManager(this)
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.rvRecommended.layoutManager = mLayoutManager
            binding.rvRecommended.addItemDecoration(VerticalItemDecoration(this))
            val moviesAdapter = MoviesListingAdapter(this)
            moviesAdapter.addAll(recommendationsList)
            binding.rvRecommended.adapter = moviesAdapter
        } else {
            binding.tvMovieDetailsProdRecommendedtxt.visibility = View.GONE
            binding.rvRecommended.visibility = View.GONE
        }
    }

    // Handle API error
    private fun errorMsg(errMsg: String) {
        if (errMsg.isEmpty())
            displayMessage(this, true, errMsg, binding.clMovieDetails, true)
        else
            displayMessage(this, true, getString(R.string.err_something_went_wrong), binding.clMovieDetails, true)
    }

    // Handle the collapsible toolbar title
    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (scrollRange == -1) scrollRange = appBarLayout.totalScrollRange
        if (scrollRange + verticalOffset == 0) {
            binding.collapsableToolbar.title = title
            isShowTitle = true
        } else if (isShowTitle) {
            binding.collapsableToolbar.title = " "
            isShowTitle = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}