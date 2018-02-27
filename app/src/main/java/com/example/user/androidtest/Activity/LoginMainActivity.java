package com.example.user.androidtest.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.user.androidtest.Interface.LoginView;
import com.example.user.androidtest.R;
import com.example.user.androidtest.ViewModal.LoginViewModal;

public class LoginMainActivity extends AppCompatActivity implements LoginView, View.OnClickListener{

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private LoginViewModal loginViewModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        initViews();
        initViewModal();

    }


    private void initViews()
    {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        username = (EditText) findViewById(R.id.loginText);
        password = (EditText) findViewById(R.id.passText);
        findViewById(R.id.loginButton).setOnClickListener(this);

    }

    public void signUp()
    {
        //bring up sign up page

    }

    private void initViewModal()
    {

    }

    @Override
    public void onClick(View v) {
        loginViewModal.validateLogin(username.getText().toString(), password.getText().toString());
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        username.setError("Email is not valid");
    }

    @Override
    public void setPasswordError() {
        password.setError("Password should contain one special character and minimum 8 characters required");
    }

    @Override public void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }



}
