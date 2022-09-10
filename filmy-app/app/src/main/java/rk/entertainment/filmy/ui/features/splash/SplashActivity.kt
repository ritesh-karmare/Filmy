package rk.entertainment.filmy.ui.features.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import rk.entertainment.filmy.R
import rk.entertainment.filmy.ui.features.moviesListing.MoviesListingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startHandler()
    }

    private fun startHandler() {
        lifecycleScope.launchWhenCreated {
            delay(1000)
            startActivity(Intent(this@SplashActivity, MoviesListingActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}