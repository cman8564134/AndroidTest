package com.example.user.androidtest.Modal;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by User on 26/2/2018.
 */

public class Person  extends RealmObject{
    @Required
    private String firstName;
    @Required
    private String lastName;
    @Required
    private String Type;
    @Required
    private String phoneNo;

    public Person(String firstname, String lastname, UserType type,String phoneNo)
    {
        this.firstName=firstname;
        this.lastName=lastname;
        this.Type=type.name();
        this.phoneNo=phoneNo;

    }
    public Person(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserType getType() {
        return UserType.valueOf(Type);
    }

    public void setType(UserType userType) {this.Type=userType.name();}

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
