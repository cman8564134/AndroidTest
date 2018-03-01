package com.example.user.androidtest.ViewModal;

import android.content.Intent;
import android.util.Patterns;

import com.example.user.androidtest.Activity.HomeActivity;
import com.example.user.androidtest.Interface.SignUpView;
import com.example.user.androidtest.Modal.Account;
import com.example.user.androidtest.Modal.Person;

import io.realm.Realm;

/**
 * Created by User on 26/2/2018.
 */

public class SignUpViewModal implements SignUpView {

    Person person;

    public void validateRegister(String userType,String firstName, String lastName, String phoneNumber)
    {

        if(isValidPhoneNumber(phoneNumber))
            registerAccount(new Account());

    }

    @Override
    public void setError(String type, String message) {

    }

    @Override
    public void startHomeActivity() {

    }

    private boolean isValidPhoneNumber(String phonenumber)
    {
        if(!Patterns.PHONE.matcher(phonenumber).matches()||phonenumber.length()!=10||!phonenumber.startsWith("01")) {
            setError("PhoneNumber","Phone Number Not valid");
            return false;
        }


        return true;

    }


    private void registerAccount(Account account)
    {
        final MyObject obj = new MyObject();
        obj.setId(42);
        obj.setName("Fish");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // This will create a new object in Realm or throw an exception if the
                // object already exists (same primary key)
                // realm.copyToRealm(obj);

                // This will update an existing object with the same primary key
                // or create a new object if an object with no primary key = 42
                realm.copyToRealmOrUpdate(obj);
            }
        });



    }
}
