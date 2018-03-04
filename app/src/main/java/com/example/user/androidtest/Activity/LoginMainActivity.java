package com.example.user.androidtest.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.user.androidtest.R;
import com.example.user.androidtest.ViewModal.ErrorType;
import com.example.user.androidtest.ViewModal.LoginViewModal;

import java.util.Map;

public class LoginMainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private LoginViewModal loginViewModal;
    private final String passwordEmptyMessage="Password is empty";
    private final String usernameEmptyMessage="Email is empty";
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //revert to normal theme after done loading
        setTheme(R.style.AppTheme);
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


    private void initViewModal()
    {

        loginViewModal=LoginViewModal.getInstance();

    }

    @Override
    public void onClick(View v) {
        showProgress();
        if(isValidForm()) {
            loginViewModal.validateLogin(username.getText().toString().trim(), password.getText().toString());
            if(isErrorFree()) {
                if (isExistingUser()) {
                    //Account information is cache so that user need to login only once
                    storeToSharedPreference(new String[]{loginViewModal.getAccount().getID(),loginViewModal.getAccount().getPassword()});
                    //validated user
                    startHomeActivity();
                }
                else
                    //new user
                    startSignUpActivity();
            }
        }

        hideProgress();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    //to show and hide progress bar
    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }


    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    //navigate to signup activity
    private void startSignUpActivity()
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    //check whether any of the required fields are empty
    private boolean isValidForm()
    {

        if(username.getText().toString().trim().isEmpty()||password.getText().toString().isEmpty()) {
            Snackbar.make(findViewById(R.id.LoginParentLayout), "Please Complete The Form!", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //check whether the user logging in is an existing user before sending to sign up activity
    private boolean isExistingUser(){return loginViewModal.getAccount().getUser()!=null;}


    //show error messages according to their error type
    private void setError(ErrorType inputType, String message)
    {
        if(inputType.equals(ErrorType.Username))
        {
            setUsernameError(message);
        }
        else if(inputType.equals(ErrorType.Password))
        {
            setPasswordError(message);
        }

    }

    //set the error message at the username text field
    private void setUsernameError(String message) {
        username.requestFocus();
        username.setError(message);
    }

    //set the error message at the password text field
    private void setPasswordError(String message) {
        password.requestFocus();
        password.setError(message);
    }

    //navigate to home activity when the user is validated
    private void startHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    //check whether there is any error at the view model (observer replacement)
    private boolean isErrorFree()
    {
        if(!loginViewModal.getErrorMessages().isEmpty())
        {
            for (Map.Entry<ErrorType,String> entry : loginViewModal.getErrorMessages().entrySet()) {
                setError(entry.getKey(),entry.getValue());
            }

            loginViewModal.getErrorMessages().clear();
            return false;
        }
        return true;

    }


    //cache account data if it is a valid login
    private void storeToSharedPreference(String[] account)
    {
        SharedPreferences prefs = getSharedPreferences("UserAccountData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", account[0]);
        editor.putString("password", account[1]);
        editor.commit();
    }



}
