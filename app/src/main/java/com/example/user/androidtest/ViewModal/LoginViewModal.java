package com.example.user.androidtest.ViewModal;

import android.databinding.BaseObservable;

import com.example.user.androidtest.Interface.LoginView;
import com.example.user.androidtest.Modal.Account;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by User on 26/2/2018.
 */

public class LoginViewModal extends BaseObservable implements LoginView {

    Account account;
    Realm realm =Realm.getDefaultInstance();

    public LoginViewModal()
    {

    }


    public void validateLogin(String username,String password)
    {
        showProgress();

        checkInput();
        checkDatabase();

        hideProgress();
    }

    private void checkDatabase()
    {
        RealmQuery query = realm.where(Account.class);
        Account acc= (Account) query.equalTo("ID",username).findFirst();
        if(acc==null)
        {
            // redirect to SignUP

            Account account=new Account(username,password);
        }
        else
            //redirect to HOME
            this.account=acc;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setUsernameError() {

    }

    @Override
    public void setPasswordError() {

    }

    @Override
    public void navigateToHome() {

    }
}
