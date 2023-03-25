package rk.entertainment.movie_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import rk.entertainment.common.navigation.MovieDetailsNavigation

class MovieDetailsNavigationImpl : MovieDetailsNavigation {
    override fun newIntent(context: Context, args: Bundle?): Intent =
        Intent(context, MovieDetailsActivity::class.java).apply {
            args?.let { putExtras(it) }
        }
}