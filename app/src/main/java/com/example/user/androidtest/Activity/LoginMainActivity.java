package com.example.user.androidtest.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.example.user.androidtest.Modal.Account;
import com.example.user.androidtest.R;
import com.example.user.androidtest.ViewModal.ErrorType;
import com.example.user.androidtest.ViewModal.LoginViewModal;

import java.util.HashMap;
import java.util.Map;

public class LoginMainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private LoginViewModal loginViewModal;
    private final String passwordEmptyMessage="Password is empty";
    private final String usernameEmptyMessage="Email is empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //revert to normal theme after done loading
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        initViews();
        initViewModal();

    }

    private void storeToSharedPreference(Account account)
    {
        SharedPreferences prefs = getSharedPreferences("UserAccountData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", account.getID());
        editor.putString("password", account.getPassword());
        editor.commit();
    }


    private void initViews()
    {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        username = (EditText) findViewById(R.id.loginText);
        password = (EditText) findViewById(R.id.passText);
        findViewById(R.id.loginButton).setOnClickListener(this);

    }


    private void startSignUpActivity()
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private boolean isValidForm()
    {

        if(username.getText().toString().trim().isEmpty()||password.getText().toString().isEmpty()) {
            Snackbar.make(findViewById(R.id.LoginParentLayout), "Please Complete The Form!", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
                    storeToSharedPreference(loginViewModal.getAccount());
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


    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }


    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private boolean isExistingUser(){
        System.out.println("isExistingUser()");
        System.out.println(loginViewModal.getAccount());
        return loginViewModal.getAccount().getUser()!=null;}


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

    private void setUsernameError(String message) {
        username.requestFocus();
        username.setError(message);
    }

    private void setPasswordError(String message) {
        password.requestFocus();
        password.setError(message);
    }


    private void startHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

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





}
