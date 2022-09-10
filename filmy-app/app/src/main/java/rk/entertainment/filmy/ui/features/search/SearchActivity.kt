package rk.entertainment.filmy.ui.features.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import rk.entertainment.filmy.R
import rk.entertainment.filmy.data.models.movieList.MoviesListData
import rk.entertainment.filmy.databinding.ActivitySearchBinding
import rk.entertainment.filmy.ui.features.movieDetails.MovieDetailsActivity
import rk.entertainment.filmy.ui.features.moviesListing.MovieClickListener
import rk.entertainment.filmy.ui.features.moviesListing.MoviesListingAdapter
import rk.entertainment.filmy.utils.ConnectionUtils
import rk.entertainment.filmy.utils.EndlessRecyclerViewOnScrollListener
import rk.entertainment.filmy.utils.Logs
import rk.entertainment.filmy.utils.UIUtils
import rk.entertainment.filmy.utils.UIUtils.displayMessage

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), MovieClickListener {

    private var endlessRecyclerViewOnScrollListener: EndlessRecyclerViewOnScrollListener? = null

    private var adapter: MoviesListingAdapter? = null

    private var searchMovieViewModel: SearchMovieViewModel? = null
    private lateinit var binding: ActivitySearchBinding

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var query: String? = null
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        initToolbar()
        initRv()
        initListeners()
        initViewModel()
        initObservers()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbSearch)
        binding.tbSearch.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRv() {
        binding.rvSearch.apply {
            this@SearchActivity.adapter =
                MoviesListingAdapter(context = this@SearchActivity, listener = this@SearchActivity)
            adapter = this@SearchActivity.adapter
        }
    }


    private fun initListeners() {

        binding.ivClose.setOnClickListener {
            binding.etSearch.setText("")
        }

        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            if(text.toString().trim().isEmpty()) return@doOnTextChanged

            searchJob?.cancel()
            searchJob = coroutineScope.launch {
                delay(1500)
                onQuery(text.toString())
            }
        }

        binding.rvSearch.addOnScrollListener(object : EndlessRecyclerViewOnScrollListener(
            binding.rvSearch.layoutManager as GridLayoutManager
        ) {
            override fun onLoadMore() {
                toggleListenerLoading(true)
                getMovies()
            }
        }.also { endlessRecyclerViewOnScrollListener = it })
    }

    private fun initViewModel() {
        searchMovieViewModel = ViewModelProvider(this).get(SearchMovieViewModel::class.java)
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                searchMovieViewModel?.movieListStateFlow?.collect {

                    UIUtils.hideKeyboard(this@SearchActivity, binding.etSearch)

                    if(!it.error.isNullOrEmpty()) {
                        Logs.i("TAG", "searchMovies: movieList error")
                        errorMsg(it.error)
                    }

                    it.movieDetails?.let { moviesListResponse ->
                        Logs.i("TAG", "searchMovies: movieListResponse")
                        if(moviesListResponse.results.isNotEmpty()) {
                            displayMoviesList(moviesListResponse.results)
                        }else{
                            errorMsg(getString(R.string.no_movies_found))
                        }
                    }
                }
            }
        }
    }

    // Call movies data with searched query
    private fun onQuery(s: String) {
        query = s
        getMovies()
    }

    // trigger presenter to get movies for searched query
    private fun getMovies() {
        if(ConnectionUtils.isNetworkAvailable()) {
            searchMovieViewModel?.searchMovies(query!!)
        } else
            errorMsg(getString(R.string.no_internet_connection))
    }

    private fun displayMoviesList(upcomingMoviesList: List<MoviesListData>) {
        adapter?.let {

            if(searchMovieViewModel?.page == 1)
                it.clear()

            if(it.itemCount > 0) {
                toggleListenerLoading(false)
                binding.rvSearch.stopScroll()
            }
            it.addAll(upcomingMoviesList)
        }
    }

    // Display API error
    private fun errorMsg(errMsg: String?) {
        toggleListenerLoading(false)
        val msg = errMsg ?: getString(R.string.err_something_went_wrong)
        displayMessage(this@SearchActivity, msg, binding.clSearch)
    }

    // Set the ScrollListener to true/false when end of recyclerview reached for loading more data
    private fun toggleListenerLoading(isLoading: Boolean) {
        endlessRecyclerViewOnScrollListener!!.setLoading(isLoading)
    }

    override fun onMovieItemClicked(movieId: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }
}