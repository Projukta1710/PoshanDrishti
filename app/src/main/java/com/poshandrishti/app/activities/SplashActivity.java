

package com.poshandrishti.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.poshandrishti.app.R;
import com.poshandrishti.app.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Full screen mode
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // Load splash screen
        setContentView(R.layout.activity_splash);

        // Navigate after 2.5 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            Intent intent = new Intent(
                    SplashActivity.this,
                    MainActivity.class
            );

            startActivity(intent);
            finish();

        }, 2500);
    }
}
