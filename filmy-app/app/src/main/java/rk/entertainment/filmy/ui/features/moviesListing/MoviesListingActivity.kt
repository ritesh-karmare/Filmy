package rk.entertainment.filmy.ui.features.moviesListing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import rk.entertainment.filmy.R
import rk.entertainment.filmy.databinding.ActivityMoviesMainBinding
import rk.entertainment.filmy.ui.features.search.SearchActivity
import rk.entertainment.filmy.data.models.MovieModuleTypes
import rk.entertainment.filmy.utils.ViewPagerAdapter

@AndroidEntryPoint
class MoviesListingActivity : AppCompatActivity() {

    private val tabLabelArr: Array<String> by lazy {
        resources.getStringArray(R.array.tab_labels)
    }

    private var _doubleBackToExitPressedOnce = false

    private lateinit var binding: ActivityMoviesMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies_main)
        initToolbar()
        setupViewPager(binding.viewpager)
        initTabs()
        initListeners()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) supportActionBar!!.setDisplayShowTitleEnabled(false)
        binding.tvToolbarTitle.letterSpacing = 0.15f
    }

    private fun initListeners() {
        binding.ivSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(selectedTab: TabLayout.Tab) {
                updateTabs(selectedTab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    // Setting up the viewPager with titles -> NowPLaying, Upcoming, TopRated, etc...
    private fun setupViewPager(viewPager: ViewPager) {
        val pageAdapter = ViewPagerAdapter(supportFragmentManager)
        pageAdapter.apply {
            addFragment(
                MoviesListingFragment.newInstance(MovieModuleTypes.NOW_PLAYING),
                tabLabelArr[0]
            )

            addFragment(
                MoviesListingFragment.newInstance(MovieModuleTypes.UPCOMING),
                tabLabelArr[1]
            )

            addFragment(
                MoviesListingFragment.newInstance(MovieModuleTypes.TOP_RATED),
                tabLabelArr[2]
            )

            addFragment(MoviesListingFragment.newInstance(MovieModuleTypes.POPULAR), tabLabelArr[3])
        }

        viewPager.apply {
            adapter = pageAdapter
            offscreenPageLimit = pageAdapter.count
        }

        binding.tabs.setupWithViewPager(viewPager)
    }

    // Initialize tabs with selected/default state; tab and text color
    private fun initTabs() {
        for (i in 0 until binding.tabs.tabCount) {
            val tabOne = LayoutInflater.from(this)
                .inflate(R.layout.custom_tab_layout, null) as AppCompatTextView
            tabOne.text = tabLabelArr[i]

            if (binding.tabs.selectedTabPosition == i) {
                tabOne.setBackgroundResource(R.drawable.tab_selected_bg)
                tabOne.setTextColor(resources.getColor(android.R.color.white))
            } else {
                tabOne.setBackgroundResource(R.drawable.tab_unselected_bg)
                tabOne.setTextColor(resources.getColor(R.color.light_gray))
            }

            val tab = binding.tabs.getTabAt(i)
            if (tab != null) tab.customView = tabOne
        }
    }

    // Update tabs with selected/default state; tab and text color on sliding pager
    private fun updateTabs(selectedTab: TabLayout.Tab) {
        for (i in 0 until binding.tabs.tabCount) {
            val tab = binding.tabs.getTabAt(i)
            if (tab != null) {
                val tv = tab.customView as AppCompatTextView
                if (tab.position == selectedTab.position) {
                    tv.setBackgroundResource(R.drawable.tab_selected_bg)
                    tv.setTextColor(ContextCompat.getColor(this, android.R.color.white))
                } else {
                    tv.setBackgroundResource(R.drawable.tab_unselected_bg)
                    tv.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
                }
            }
        }
    }
}