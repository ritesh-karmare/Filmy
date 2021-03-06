package rk.entertainment.filmy.modules.movies;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import rk.entertainment.filmy.R;
import rk.entertainment.filmy.modules.search.SearchActivity;
import rk.entertainment.filmy.utils.MovieModuleTypes;
import rk.entertainment.filmy.utils.ViewPagerAdapter;

public class MoviesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.iv_search)
    ImageView ivSearch;

    private String[] tabLabelArr;
    private boolean _doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_main);
        ButterKnife.bind(this);
        initToolbar();
        initReferences();
        setupViewPager(viewPager);
        initTabs();
        initListeners();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            tv_toolbar_title.setLetterSpacing(0.15f);
    }

    private void initReferences() {
        tabLabelArr = getResources().getStringArray(R.array.tab_labels);
    }

    private void initListeners() {

        ivSearch.setOnClickListener(view ->
                startActivity(new Intent(MoviesActivity.this, SearchActivity.class)));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab selectedTab) {
                updateTabs(selectedTab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    // Setting up the viewPager with titles -> NowPLaying, Upcoming, TopRated, etc...
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MoviesFragment.newInstance(MovieModuleTypes.NOW_PLAYING), tabLabelArr[0]);
        adapter.addFragment(MoviesFragment.newInstance(MovieModuleTypes.UPCOMING), tabLabelArr[1]);
        adapter.addFragment(MoviesFragment.newInstance(MovieModuleTypes.TOP_RATED), tabLabelArr[2]);
        adapter.addFragment(MoviesFragment.newInstance(MovieModuleTypes.POPULAR), tabLabelArr[3]);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
    }

    // Initialize tabs with selected/default state; tab and text color
    private void initTabs() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            AppCompatTextView tabOne = (AppCompatTextView) LayoutInflater.from(this)
                    .inflate(R.layout.custom_tab_layout, null);
            tabOne.setText(tabLabelArr[i]);
            if (tabLayout.getSelectedTabPosition() == i) {
                tabOne.setBackgroundResource(R.drawable.tab_selected_bg);
                tabOne.setTextColor(getResources().getColor(android.R.color.white));
            } else {
                tabOne.setBackgroundResource(R.drawable.tab_unselected_bg);
                tabOne.setTextColor(getResources().getColor(R.color.light_gray));
            }
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) tab.setCustomView(tabOne);
        }
    }

    // Update tabs with selected/default state; tab and text color on sliding pager
    private void updateTabs(TabLayout.Tab selectedTab) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                AppCompatTextView tv = (AppCompatTextView) tab.getCustomView();
                if (tv != null)
                    if (tab.getPosition() == selectedTab.getPosition()) {
                        tv.setBackgroundResource(R.drawable.tab_selected_bg);
                        tv.setTextColor(getResources().getColor(android.R.color.white));
                    } else {
                        tv.setBackgroundResource(R.drawable.tab_unselected_bg);
                        tv.setTextColor(getResources().getColor(R.color.light_gray));
                    }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (_doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this._doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> _doubleBackToExitPressedOnce = false, 1000);
    }
}