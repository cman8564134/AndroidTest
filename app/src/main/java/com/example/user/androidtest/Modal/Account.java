package com.example.user.androidtest.Modal;

import io.realm.Realm;
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
    private String password;
    @Required
    private Person user;

    public Account()
    {

    }

    public Account(String id, String password)
    {
        this.ID=id;
        this.password=password;
    }

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
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(Person user) {
        this.user = user;
    }



}
