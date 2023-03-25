package rk.entertainment.movie_details

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import rk.entertainment.movie_details.databinding.ActivityTrailerBinding


class TrailerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private val DEVELOPER_KEY = BuildConfig.YOUTUBE_API_KEY
    private var YOUTUBE_VIDEO_CODE: String? = null

    private lateinit var binding: ActivityTrailerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrailerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initReferences()
    }

    private fun initReferences() {
        if (intent != null && intent.hasExtra("video_id"))
            YOUTUBE_VIDEO_CODE = intent.getStringExtra("video_id")

        // Initializing video player with developer key
        binding.ytPlayer.initialize(DEVELOPER_KEY, this)
    }

    // Callback for failure in case player unable to play video
    override fun onInitializationFailure(provider: YouTubePlayer.Provider,
                                         errorReason: YouTubeInitializationResult) {
        if (errorReason.isUserRecoverableError)
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show()
        else {
            val errorMessage = String.format(getString(R.string.error_player), errorReason.toString())
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    // Callback for player initialized successfully
    override fun onInitializationSuccess(provider: YouTubePlayer.Provider,
                                         player: YouTubePlayer, wasRestored: Boolean) {
        if (!wasRestored) {
            // loadVideo() will auto play video; cueVideo() method wont
            player.loadVideo(YOUTUBE_VIDEO_CODE)
            // Display
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
        }
    }

    // If onError, player requested to retry loading
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            binding.ytPlayer.initialize(DEVELOPER_KEY, this)
        }
    }

    companion object {
        private const val RECOVERY_DIALOG_REQUEST = 1
    }
}