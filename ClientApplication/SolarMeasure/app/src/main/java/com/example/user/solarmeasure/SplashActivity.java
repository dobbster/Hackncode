package com.example.user.solarmeasure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.itangqi.waveloadingview.WaveLoadingView;

public class SplashActivity extends AppCompatActivity {

    WaveLoadingView mWaveLoadingView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        final String restoredName = prefs.getString("name", null);

        mWaveLoadingView2 = findViewById(R.id.waveLoadingView2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(restoredName != null)
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
            }
        }, 1000);
    }
}
