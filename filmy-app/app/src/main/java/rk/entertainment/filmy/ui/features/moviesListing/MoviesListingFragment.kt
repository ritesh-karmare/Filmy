package rk.entertainment.filmy.ui.features.moviesListing

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import rk.entertainment.filmy.R
import rk.entertainment.filmy.data.models.movieList.MoviesListData
import rk.entertainment.filmy.databinding.FragmentMoviesListBinding
import rk.entertainment.filmy.utils.ConnectionUtils
import rk.entertainment.filmy.utils.Logs
import rk.entertainment.filmy.utils.MovieModuleTypes
import rk.entertainment.filmy.utils.UIUtils.displayMessage
import rk.entertainment.filmy.utils.UIUtils.dpToPx
import rk.entertainment.filmy.utils.rvUtils.EndlessRecyclerViewOnScrollListener
import rk.entertainment.filmy.utils.rvUtils.GridSpacingItemDecoration

@AndroidEntryPoint
class MoviesListingFragment : Fragment() {

    private val TAG = "MoviesListingFragment"

    private var movieModuleType = MovieModuleTypes.UPCOMING
    private var adapter: MoviesListingAdapter? = null

    private lateinit var mGridLayoutManager: GridLayoutManager
    private lateinit var endlessRecyclerViewOnScrollListener: EndlessRecyclerViewOnScrollListener
    private lateinit var mContext: Context

    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var moviesListingViewModel: MoviesListingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieModuleType = arguments?.getSerializable(ARG_KEY) as MovieModuleTypes
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
        binding.rvUpcomingMovies.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(8f, mContext), true))
        adapter = MoviesListingAdapter(mContext)

        binding.rvUpcomingMovies.adapter = adapter
    }

    private fun initListeners() {

        binding.swipeRefreshUpcomingMovies.setOnRefreshListener { getMovies(true) }

        binding.rvUpcomingMovies.addOnScrollListener(object : EndlessRecyclerViewOnScrollListener(mGridLayoutManager) {
            override fun onLoadMore() {
                toggleListenerLoading(true)
                getMovies(false)
            }
        }.also { endlessRecyclerViewOnScrollListener = it })
    }

    private fun initViewModel() {
        moviesListingViewModel = ViewModelProvider(this).get(movieModuleType.name, MoviesListingViewModel::class.java)
        getMovies(false)
    }

    private fun initObservers() {
        try {
            moviesListingViewModel.errorListener.observe(viewLifecycleOwner, { message: String -> errorMsg(message) })
        } catch (e: Exception) {
            Logs.logException(e)
        }
    }

    // Trigger viewModel to get the list of movies for specific MovieModuleType
    private fun getMovies(isRefreshed: Boolean) {
        Logs.i(TAG, "getMovies " + movieModuleType.name)

        if (ConnectionUtils.isNetworkAvailable()) {
            if (isRefreshed) {
                adapter?.clear()
                moviesListingViewModel.resetPage()
            }
            binding.swipeRefreshUpcomingMovies.isRefreshing = true

            moviesListingViewModel.getMovies(movieModuleType, "").observe(viewLifecycleOwner, {
                if (moviesListingViewModel.addMore())
                    displayMoreMoviesList(it.results)
                else
                    displayMoviesList(it.results)
            })

        } else
            displayMessage(mContext, true, getString(R.string.no_internet_connection), binding.swipeRefreshUpcomingMovies, true)
    }

    private fun displayMoviesList(upcomingMoviesList: List<MoviesListData>) {
        dismissRefreshing()
        loadAdapter(upcomingMoviesList)
    }

    private fun displayMoreMoviesList(upcomingMoviesList: List<MoviesListData>) {
        dismissRefreshing()
        toggleListenerLoading(false)
        loadAdapter(upcomingMoviesList)
        binding.rvUpcomingMovies.stopScroll()
    }

    // Handle API error
    private fun errorMsg(_message: String) {
        var message = _message
        dismissRefreshing()
        toggleListenerLoading(false)

        if (TextUtils.isEmpty(message)) message = getString(R.string.err_something_went_wrong)
        displayMessage(mContext, true, message, binding.swipeRefreshUpcomingMovies, true)
    }

    // Load the adapter with movie list
    private fun loadAdapter(upcomingMoviesList: List<MoviesListData>) {
        adapter?.addAll(upcomingMoviesList)
    }

    // Dismiss swipe refresh
    private fun dismissRefreshing() {
        binding.swipeRefreshUpcomingMovies.isRefreshing = false
    }

    // Set the ScrollListener to true/false when end of recyclerview reached for loading more data
    private fun toggleListenerLoading(isLoading: Boolean) {
        endlessRecyclerViewOnScrollListener.setLoading(isLoading)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
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
}