package com.example.user.androidtest.Modal;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Created by User on 3/3/2018.
 */
public class AccountTest {
    private Account account;

    @Before
    public void setup()
    {

        account=new Account();
    }

    @Test
    public void accIsNotNull() {
        assertNotNull(account);
    }
    @Test
    public void factoryConstrutor()throws Exception
    {
        assertNull(account.getUser());
        assertNull(account.getID());
        assertNull(account.getPassword());
    }

    @Test
    public void accountConstructor()throws Exception{

        account=new Account("SomeID","somePass");
        assertNotNull(account.getID());
        assertNotNull(account.getPassword());
    }





    @Test
    public void setID() throws Exception {
        account.setID("AnotherID");
        assertEquals("AnotherID",account.getID());
    }



    @Test
    public void setPassword() throws Exception {
        account.setPassword("Anotherpass");
        assertEquals("Anotherpass",account.getPassword());
    }

    @Test
    public void getUser() throws Exception {
        //user is null by default
        assertNull(account.getUser());
    }
    @Test
    public void setUser() throws Exception {
        account.setUser(new Person("first","",UserType.Private,""));
        assertNotNull(account.getUser());
    }


}