package com.example.user.androidtest.Modal;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by User on 27/2/2018.
 */

public class Account extends RealmObject{

    @PrimaryKey @Required
    private String ID;
    @Required
    private String Password;

    private Person user;

    public Account(String id, String password)
    {
        this.ID=id;
        this.Password =password;
    }
    public Account(){}



    public String getID() {
        return ID;
    }

    public Person getUser()
    {
        return user;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public void setUser(Person user) {
        this.user = user;
    }





}
