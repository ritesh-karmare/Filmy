package rk.entertainment.filmy.ui.features.moviesListing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rk.entertainment.filmy.R
import rk.entertainment.filmy.data.models.movieList.MoviesListData
import rk.entertainment.filmy.databinding.FragmentMoviesListBinding
import rk.entertainment.filmy.ui.features.movieDetails.MovieDetailsActivity
import rk.entertainment.filmy.utils.ConnectionUtils
import rk.entertainment.filmy.utils.Logs
import rk.entertainment.filmy.utils.MovieModuleTypes
import rk.entertainment.filmy.utils.UIUtils.displayMessage
import rk.entertainment.filmy.utils.UIUtils.dpToPx
import rk.entertainment.filmy.utils.rvUtils.EndlessRecyclerViewOnScrollListener
import rk.entertainment.filmy.utils.rvUtils.GridSpacingItemDecoration

@AndroidEntryPoint
class MoviesListingFragment : Fragment(), MovieClickListener {

    private val TAG = "MoviesListingFragment"

    private var movieModuleType = MovieModuleTypes.UPCOMING
    private var adapter: MoviesListingAdapter? = null

    private lateinit var mGridLayoutManager: GridLayoutManager
    private lateinit var endlessRecyclerViewOnScrollListener: EndlessRecyclerViewOnScrollListener

    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var moviesListingViewModel: MoviesListingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieModuleType = arguments?.getSerializable(ARG_KEY) as MovieModuleTypes
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReferences()
        initListeners()
        initViewModel()
        initObservers()
    }

    private fun initReferences() {
        mGridLayoutManager = GridLayoutManager(getContext(), 2)
        binding.rvUpcomingMovies.layoutManager = mGridLayoutManager
        binding.rvUpcomingMovies.itemAnimator = DefaultItemAnimator()
        binding.rvUpcomingMovies.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                dpToPx(
                    8f,
                    requireContext()
                ),
                true
            )
        )
        adapter = MoviesListingAdapter(this)

        binding.rvUpcomingMovies.adapter = adapter
    }

    private fun initListeners() {

        binding.swipeRefreshUpcomingMovies.setOnRefreshListener { getMovies(true) }

        binding.rvUpcomingMovies.addOnScrollListener(object : EndlessRecyclerViewOnScrollListener(
            mGridLayoutManager
        ) {
            override fun onLoadMore() {
                toggleListenerLoading(true)
                getMovies(false)
            }
        }.also { endlessRecyclerViewOnScrollListener = it })
    }

    private fun initViewModel() {
        moviesListingViewModel =
            ViewModelProvider(this).get(movieModuleType.name, MoviesListingViewModel::class.java)
        getMovies(false)
    }

    private fun initObservers() {
        try {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    moviesListingViewModel.movieListStateFlow.collect {

                        if(!it.error.isNullOrEmpty())
                            errorMsg(it.error)

                        it.movieDetails?.let { movieListResponse ->
                            if(movieListResponse.results.isNotEmpty()) {
                                displayMoviesList(movieListResponse.results)
                            }
                        }
                    }
                }

            }
        } catch (e: Exception) {
            Logs.logException(e)
        }
    }

    // Trigger viewModel to get the list of movies for specific MovieModuleType
    private fun getMovies(isRefreshed: Boolean) {
        Logs.i(TAG, "getMovies " + movieModuleType.name)

        if(ConnectionUtils.isNetworkAvailable()) {
            if(isRefreshed) {
                adapter?.clear()
                moviesListingViewModel.resetPage()
            }
            binding.swipeRefreshUpcomingMovies.isRefreshing = true

            moviesListingViewModel.getMovies(movieModuleType)

        } else
            errorMsg(getString(R.string.no_internet_connection))
    }

    private fun displayMoviesList(upcomingMoviesList: List<MoviesListData>) {
        adapter?.let {
            if(it.itemCount > 0) {
                toggleListenerLoading(false)
                binding.rvUpcomingMovies.stopScroll()
            }
            it.addAll(upcomingMoviesList)
        }
        dismissRefreshing()
    }

    // Handle API error
    private fun errorMsg(_message: String?) {
        var message = _message ?: getString(R.string.err_something_went_wrong)
        dismissRefreshing()
        toggleListenerLoading(false)
        displayMessage(requireContext(), message, binding.swipeRefreshUpcomingMovies)
    }

    // Dismiss swipe refresh
    private fun dismissRefreshing() {
        binding.swipeRefreshUpcomingMovies.isRefreshing = false
    }

    // Set the ScrollListener to true/false when end of recyclerview reached for loading more data
    private fun toggleListenerLoading(isLoading: Boolean) {
        endlessRecyclerViewOnScrollListener.setLoading(isLoading)
    }

    companion object {
        private const val ARG_KEY = "moviesModuleType"
        fun newInstance(movieModuleType: MovieModuleTypes?): MoviesListingFragment {
            val fragment = MoviesListingFragment()
            val args = Bundle()
            args.putSerializable(ARG_KEY, movieModuleType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onMovieItemClicked(movieId: Int) {
        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }
}
