package com.example.user.androidtest.Modal;

/**
 * Created by User on 26/2/2018.
 */

public class Person {
    private String firstName;
    private String lastName;
    private final String Type;
    private String phoneNo;

    public Person(String id, String firstname, String lastname, String type,String phoneNo)
    {
        this.firstName=firstname;
        this.lastName=lastname;
        this.Type=type;
        this.phoneNo=phoneNo;

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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
