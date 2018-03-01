package com.example.user.androidtest.Interface;

/**
 * Created by User on 26/2/2018.
 */

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setError(String type,String message);

    void startHomeActivity();
}
