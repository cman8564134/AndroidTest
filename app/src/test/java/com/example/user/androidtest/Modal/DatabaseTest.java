package com.example.user.androidtest.Modal;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by User on 3/3/2018.
 */

//unit testing for database class (however it is not possible YET because Realm does not support unit testing)
public class DatabaseTest {

    private Database database;

    @Before
    public void setup() throws Exception
    {
        database=new Database();
    }
    @Test
    public void getInstance() throws Exception {
        assertNotNull(database);
    }

    @Test
    public void registerOrUpdateAccount() throws Exception {
        Account a = new Account("someRandomID","someRandomPassword");
        a.setUser(new Person("firstName","LastName",UserType.Broker,"0124532621"));
        database.registerOrUpdateAccount(a);
        assertNotNull(database.getAccount(a));
    }


}