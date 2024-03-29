package rk.entertainment.filmy.ui.features.movieDetails

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rk.entertainment.filmy.R
import rk.entertainment.filmy.data.models.movieList.MoviesListData
import rk.entertainment.filmy.data.models.moviesDetails.*
import rk.entertainment.filmy.databinding.ActivityMovieDetailsBinding
import rk.entertainment.filmy.ui.features.moviesListing.MovieClickListener
import rk.entertainment.filmy.ui.features.moviesListing.MoviesListingAdapter
import rk.entertainment.filmy.utils.ConnectionUtils
import rk.entertainment.filmy.utils.DateTimeUtil.getHoursAndMinutes
import rk.entertainment.filmy.utils.DateTimeUtil.getYearFromDate
import rk.entertainment.filmy.utils.Logs
import rk.entertainment.filmy.utils.UIUtils.displayMessage
import rk.entertainment.filmy.utils.UIUtils.dpToPx

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity(), OnOffsetChangedListener, MovieClickListener {
    // appbar scroll range
    private var scrollRange = -1
    private var isShowTitle = true
    private var title: String? = null
    private var movieId: Int = 0

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
            initObservers()
            initListeners()
            initViewModel()
            getMovieDetails()
        } catch (e: Exception) {
            Logs.logException(e)
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetailsStateFlow.collect {

                    if(!it.error.isNullOrEmpty()) {
                        errorMsg(it.error)
                    }

                    if(it.movieDetails != null) {
                        movieDetails(it.movieDetails)
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        if(supportActionBar != null) supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.collapsableToolbar.title = " "
    }

    private fun initReferences() {
        movieId = intent.getIntExtra("movieId", -1)
    }

    private fun initListeners() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Logs.e("Selected_Page", position.toString())
                updateIndicators(position)
            }
        })
    }

    // Trigger presenter to get the movie details
    private fun getMovieDetails() {
        if(ConnectionUtils.isNetworkAvailable()) {
            viewModel.getMovieDetails(movieId)
        } else
            displayMessage(
                this, getString(R.string.no_internet_connection), binding.clMovieDetails
            )
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
        if(indicators!!.size > 0) indicators!![0].setBackgroundResource(R.drawable.indicator_selected)
    }

    // Update the indicator to selected/default state on scroll of viewPager
    private fun updateIndicators(selectedPostion: Int) {
        for (indicatorPosition in indicators!!.indices) {
            indicators!![indicatorPosition].setBackgroundResource(
                if(indicatorPosition == selectedPostion)
                    R.drawable.indicator_selected
                else R.drawable.indicator_default
            )
        }
    }

    // Movie details main response method
    fun movieDetails(movieData: MovieDetailsRes) {
        setUpMovieHeader(
            movieData.images.backdrops,
            movieData.title,
            movieData.genres,
            movieData.releaseDate,
            movieData.voteAverage,
            movieData.runtime
        )
        setUpOverView(movieData.overview)
        setUpMovieFacts(movieData.originalTitle, movieData.status, movieData.productionCompanies)
        initVideos(movieData.videos.results as ArrayList)
        initRecommendedMovies(movieData.recommendations.results)
    }

    // Set Images, title, genre, releaseDate, VoteAverage, Runtime
    private fun setUpMovieHeader(
        backdrops: List<BackdropData>, title: String, genres: List<GenreData>,
        releaseDate: String, rating: Double, runtime: Int
    ) {
        val customPagerAdapter = CustomPagerAdapter(this, backdrops as ArrayList)
        binding.viewPager.adapter = customPagerAdapter

        initDots()
        addDots(backdrops.size)
        val releaseYear = getYearFromDate(releaseDate)
        this.title = title
        binding.tvMovieDetailsName.text = title
        binding.tvMovieDetailsYear.text = releaseYear
        binding.tvMovieDetailsRating.text = String.format("%.1f", rating)

        if(!genres.isNullOrEmpty()) {
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
    private fun setUpMovieFacts(
        originalTitle: String, status: String,
        productionCompanies: List<ProductionCompanyData>
    ) {
        binding.tvMovieDetailsOrgTitle.text = originalTitle
        binding.tvMovieDetailsStatus.text = status
        binding.tvMovieDetailsProdCompanies.text =
            productionCompanies.joinToString(",") { it.name } //getAppendedStringFromList(productionCompanies)
    }

    // Set videos thumbnail in the horizontal recyclerView
    private fun initVideos(videosList: ArrayList<VideosData>) {
        if(videosList.isNotEmpty()) {
            //binding.rvVideos.addItemDecoration(VerticalItemDecoration(this))
            val videosAdapter = VideosAdapter(this, videosList)
            binding.rvVideos.adapter = videosAdapter
        } else {
            binding.tvMovieDetailsVideos.visibility = View.GONE
            binding.rvVideos.visibility = View.GONE
        }
    }

    // Set recommended movies in the horizontal recyclerView
    private fun initRecommendedMovies(recommendationsList: List<MoviesListData>) {
        if(recommendationsList.isNotEmpty()) {
            //binding.rvRecommended.addItemDecoration(VerticalItemDecoration(this))
            val moviesAdapter = MoviesListingAdapter(this, this,true)
            moviesAdapter.addAll(recommendationsList)
            binding.rvRecommended.adapter = moviesAdapter
        } else {
            binding.tvMovieDetailsProdRecommendedtxt.visibility = View.GONE
            binding.rvRecommended.visibility = View.GONE
        }
    }

    // Handle API error
    private fun errorMsg(errMsg: String) {
        if(errMsg.isEmpty())
            displayMessage(this, errMsg, binding.clMovieDetails)
        else
            displayMessage(
                this,
                getString(R.string.err_something_went_wrong),
                binding.clMovieDetails
            )
    }

    // Handle the collapsible toolbar title
    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if(scrollRange == -1) scrollRange = appBarLayout.totalScrollRange
        if(scrollRange + verticalOffset == 0) {
            binding.collapsableToolbar.title = title
            isShowTitle = true
        } else if(isShowTitle) {
            binding.collapsableToolbar.title = " "
            isShowTitle = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMovieItemClicked(movieId: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }
}