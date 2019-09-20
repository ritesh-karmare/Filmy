package rk.entertainment.filmy.modules.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import rk.entertainment.filmy.R;
import rk.entertainment.filmy.modules.movies.MoviesActivity;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startHandler();
    }

    private void startHandler() {

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MoviesActivity.class));
            overridePendingTransition(android.R.anim.fade_in ,android.R.anim.fade_out);
            finish();
        },1000);



    }


}
