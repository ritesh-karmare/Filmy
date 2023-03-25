package rk.entertainment.common.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle

interface Navigation<T : Bundle?> {
    fun newIntent(context: Context, args: T? = null): Intent
}

interface MovieDetailsNavigation : Navigation<Bundle?>
interface SearchMovieNavigation : Navigation<Bundle?>

