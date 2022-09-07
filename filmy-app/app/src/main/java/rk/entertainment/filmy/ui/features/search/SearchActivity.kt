package rk.entertainment.filmy.ui.features.search

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import rk.entertainment.filmy.R
import rk.entertainment.filmy.data.models.movieList.MoviesListData
import rk.entertainment.filmy.data.models.movieList.MoviesListResponse
import rk.entertainment.filmy.databinding.ActivitySearchBinding
import rk.entertainment.filmy.ui.features.moviesListing.MoviesListingAdapter
import rk.entertainment.filmy.ui.features.moviesListing.MoviesListingViewModel
import rk.entertainment.filmy.utils.ConnectionUtils
import rk.entertainment.filmy.utils.MovieModuleTypes
import rk.entertainment.filmy.utils.UIUtils.displayMessage
import rk.entertainment.filmy.utils.UIUtils.dpToPx
import rk.entertainment.filmy.utils.rvUtils.EndlessRecyclerViewOnScrollListener
import rk.entertainment.filmy.utils.rvUtils.GridSpacingItemDecoration

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), TextWatcher, OnTouchListener {

    private var mGridLayoutManager: GridLayoutManager? = null
    private var endlessRecyclerViewOnScrollListener: EndlessRecyclerViewOnScrollListener? = null

    private var adapter: MoviesListingAdapter? = null

    private var moviesListingViewModel: MoviesListingViewModel? = null
    private lateinit var binding: ActivitySearchBinding

    var query: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initReferences()
        initListeners()
        initViewModel()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbSearch)
        binding.tbSearch.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initReferences() {
        mGridLayoutManager = GridLayoutManager(this, 2)
        binding.rvSearch.layoutManager = mGridLayoutManager
        binding.rvSearch.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(8f, this), true))
        binding.rvSearch.itemAnimator = DefaultItemAnimator()
        adapter = MoviesListingAdapter(this)
        binding.rvSearch.adapter = adapter
    }

    private fun initListeners() {

        binding.etSearch.addTextChangedListener(this)
        binding.etSearch.setOnTouchListener(this)

        binding.rvSearch.addOnScrollListener(object : EndlessRecyclerViewOnScrollListener(mGridLayoutManager!!) {
            override fun onLoadMore() {
                toggleListenerLoading(true)
                getMovies()
            }
        }.also { endlessRecyclerViewOnScrollListener = it })
    }

    private fun initViewModel() {
        moviesListingViewModel = ViewModelProvider(this).get(MoviesListingViewModel::class.java)
        moviesListingViewModel?.errorListener?.observe(this, { msg: String? -> errorMsg(msg) })
    }

    // Call movies data with searched query
    private fun onQuery(s: String) {
        query = s
        getMovies()
    }

    // trigger presenter to get movies for searched query
    private fun getMovies() {
        if (ConnectionUtils.isNetworkAvailable()) {

            moviesListingViewModel?.getMovies(MovieModuleTypes.SEARCH, query!!)?.observe(this, { moviesListResponse: MoviesListResponse ->
                if (moviesListingViewModel!!.addMore())
                    displayMoreMoviesList(moviesListResponse.results)
                else
                    displayMoviesList(moviesListResponse.results)
            })
        } else
            displayMessage(this@SearchActivity, true, getString(R.string.no_internet_connection), binding.clSearch, true)
    }

    private fun displayMoviesList(upcomingMoviesList: List<MoviesListData>) {
        resetAdapter()
        loadAdapter(upcomingMoviesList)
    }

    private fun displayMoreMoviesList(upcomingMoviesList: List<MoviesListData>) {
        toggleListenerLoading(false)
        loadAdapter(upcomingMoviesList)
        binding.rvSearch.stopScroll()
    }

    // Display API error
    fun errorMsg(errMsg: String?) {
        var msg = errMsg
        toggleListenerLoading(false)
        if (TextUtils.isEmpty(msg)) msg = getString(R.string.err_something_went_wrong)
        displayMessage(this@SearchActivity, true, msg, binding.clSearch, true)
    }

    // Reset the recyclerview adapter
    private fun resetAdapter() {
        if (adapter!!.itemCount > 0) adapter!!.clear()
    }

    // Load recyclerview adapter with upcomingMovies data list
    private fun loadAdapter(upcomingMoviesList: List<MoviesListData>) {
        adapter!!.addAll(upcomingMoviesList)
    }

    // Set the ScrollListener to true\false when end of recyclerview reached for loading more data
    private fun toggleListenerLoading(isLoading: Boolean) {
        endlessRecyclerViewOnScrollListener!!.setLoading(isLoading)
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        if (charSequence.toString().trim().isEmpty()) return

        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            delay(1500)
            onQuery(charSequence.toString())
        }
    }

    override fun afterTextChanged(editable: Editable) {}

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (motionEvent.action == MotionEvent.ACTION_UP) {

            if (motionEvent.rawX >= binding.etSearch.right - binding.etSearch.compoundDrawables[2].bounds.width()) {
                binding.etSearch.setText("")
                return true
            }
        }
        return false
    }
}