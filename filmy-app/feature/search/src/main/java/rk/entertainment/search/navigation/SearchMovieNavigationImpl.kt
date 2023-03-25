package rk.entertainment.search.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import rk.entertainment.common.navigation.SearchMovieNavigation
import rk.entertainment.search.SearchActivity

class SearchMovieNavigationImpl : SearchMovieNavigation {
    override fun newIntent(context: Context, args: Bundle?): Intent =
        Intent(context, SearchActivity::class.java).apply {
            args?.let { putExtras(it) }
        }
}