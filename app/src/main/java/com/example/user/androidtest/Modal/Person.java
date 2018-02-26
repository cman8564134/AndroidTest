package com.example.user.androidtest.Modal;

/**
 * Created by User on 26/2/2018.
 */

public class Person {
    protected String ID;
    protected String firstName;
    protected String lastName;
    protected  String Type;

    public Person(String id, String firstname, String lastname, String type)
    {
        this.firstName=firstname;
        this.lastName=lastname;
        this.ID=id;
        this.Type=type;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
