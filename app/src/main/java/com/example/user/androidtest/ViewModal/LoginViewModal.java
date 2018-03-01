package com.example.user.androidtest.ViewModal;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.user.androidtest.Activity.SignUpActivity;
import com.example.user.androidtest.Interface.LoginView;
import com.example.user.androidtest.Modal.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by User on 26/2/2018.
 */

public class LoginViewModal extends ViewModel implements LoginView {

    Account account;
    Realm realm =Realm.getDefaultInstance();
    private final String passwordErrorMessage="Password should contain one special character and minimum 8 characters required";
    private HashMap<String,String> errorMessages=new HashMap<>();




    public void validateLogin(String username,String password)
    {

        if(isValidInput(username,password))
        {
            account=new Account(username,password);
            checkDatabase(account);
        }
    }

    private void checkDatabase(Account acc)
    {
        Account specificPerson = realm.where(Account.class)
                .equalTo("ID", acc.getID())
                .findFirst();

        if(specificPerson!=null) {
            //existing ID
            //check password
            if(!specificPerson.getPassword().equals(acc.getPassword())) {
                setError("Password", "Wrong Password");
                return;
            }
            //redirect to homepage
            startHomeActivity();

        }
        else
        {
            //new ID

            //redirect to signUp

        }

    }


    private boolean isValidInput(String username,String password)
    {

        if(!isValidEmail(username))
        {
            setError("Username","Email is not valid");
            return false;
        }
        else if (!isValidPassword(password))
        {
            setError("Password",passwordErrorMessage);
            return false;
        }
        return true;


    }

    private boolean isValidPassword(String password)
    {
        if(password.length()<8)
            return false;
        else if (!isValidPattern(password))
        {
            return false;
        }
        return true;

    }

    private boolean isValidPattern(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void showProgress() {
        //send back to view

    }

    @Override
    public void hideProgress() {
        //send back to view

    }

    @Override
    public void setError(String type, String message) {
        //send back to view

    }


    @Override
    public void startHomeActivity() {

    }

    public Account getAccount()
    {
        return this.account;
    }
}
