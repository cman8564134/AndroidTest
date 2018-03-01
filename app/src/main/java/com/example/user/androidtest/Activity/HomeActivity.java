package com.example.user.androidtest.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.androidtest.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        checkSharedPreferences();

        //revert to normal theme after done loading
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void checkSharedPreferences()
    {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        if(prefs.getString("username","")!="")
        {
            String username = prefs.getString("username","");
            String pwd = prefs.getString("password","");

        }
        else
        {
            //login redirect
            Intent intent = new Intent(this, LoginMainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
