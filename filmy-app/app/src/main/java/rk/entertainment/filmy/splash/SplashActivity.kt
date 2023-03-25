package rk.entertainment.filmy.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import rk.entertainment.filmy.R
import rk.entertainment.movie_listing.MoviesListingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startHandler()
    }

    private fun startHandler() {
        lifecycleScope.launchWhenCreated {
            delay(1000)
            val intent = Intent(this@SplashActivity,MoviesListingActivity::class.java)
            //intent.setClassName(packageName,"rk.entertainment.movie_listing.MoviesListingActivity")
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}