package rk.entertainment.filmy.modules.movieDetails;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rk.entertainment.filmy.BuildConfig;
import rk.entertainment.filmy.R;

public class TrailerActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    @BindView(R.id.yt_player)
    YouTubePlayerView yt_player;

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private final String DEVELOPER_KEY = BuildConfig.YOUTUBE_API_KEY;
    private String YOUTUBE_VIDEO_CODE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        ButterKnife.bind(this);
        initReferences();
    }

    private void initReferences() {
        if (getIntent() != null && getIntent().hasExtra("video_id"))
            YOUTUBE_VIDEO_CODE = getIntent().getStringExtra("video_id");
        // Initializing video player with developer key
        yt_player.initialize(DEVELOPER_KEY, this);
    }

    // Callback for failure in case player unable to play video
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError())
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    // Callback for player initialized successfully
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            // loadVideo() will auto play video; cueVideo() method wont
            player.loadVideo(YOUTUBE_VIDEO_CODE);
            // Display
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        }
    }

    // If onError, player requested to retry loading
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            yt_player.initialize(DEVELOPER_KEY, this);
        }
    }
}
