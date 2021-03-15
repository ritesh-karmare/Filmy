package rk.entertainment.filmy.view.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import rk.entertainment.filmy.R
import rk.entertainment.filmy.view.features.moviesListing.MoviesListingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startHandler()
    }

    private fun startHandler() {
        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, MoviesListingActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 1000)
    }
}