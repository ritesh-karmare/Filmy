package rk.entertainment.filmy.ui.features.moviesListing

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import rk.entertainment.filmy.R
import rk.entertainment.filmy.databinding.ActivityMoviesMainBinding
import rk.entertainment.filmy.utils.MovieModuleTypes
import rk.entertainment.filmy.ui.commons.adapter.ViewPagerAdapter
import rk.entertainment.filmy.ui.features.search.SearchActivity

class MoviesListingActivity : AppCompatActivity() {

    private lateinit var tabLabelArr: Array<String>
    private var _doubleBackToExitPressedOnce = false
    private lateinit var binding: ActivityMoviesMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initReferences()
        setupViewPager(binding.viewpager)
        initTabs()
        initListeners()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) supportActionBar!!.setDisplayShowTitleEnabled(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) binding.tvToolbarTitle.letterSpacing = 0.15f
    }

    private fun initReferences() {
        tabLabelArr = resources.getStringArray(R.array.tab_labels)
    }

    private fun initListeners() {
        binding.ivSearch.setOnClickListener { view: View? -> startActivity(Intent(this@MoviesListingActivity, SearchActivity::class.java)) }
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
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MoviesListingFragment.newInstance(MovieModuleTypes.NOW_PLAYING), tabLabelArr[0])
        adapter.addFragment(MoviesListingFragment.newInstance(MovieModuleTypes.UPCOMING), tabLabelArr[1])
        adapter.addFragment(MoviesListingFragment.newInstance(MovieModuleTypes.TOP_RATED), tabLabelArr[2])
        adapter.addFragment(MoviesListingFragment.newInstance(MovieModuleTypes.POPULAR), tabLabelArr[3])
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = adapter.count
        binding.tabs.setupWithViewPager(viewPager)
    }

    // Initialize tabs with selected/default state; tab and text color
    private fun initTabs() {
        for (i in 0 until binding.tabs.tabCount) {
            val tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null) as AppCompatTextView
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

    override fun onBackPressed() {
        if (_doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        _doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ _doubleBackToExitPressedOnce = false }, 1000)
    }
}