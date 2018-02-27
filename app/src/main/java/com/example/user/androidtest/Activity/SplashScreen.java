package com.example.user.androidtest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.user.androidtest.Activity.HomeActivity;
import com.example.user.androidtest.R;

/**
 * Created by User on 28/2/2018.
 */

public class SplashScreen extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();


    }
}
