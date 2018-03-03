package com.example.user.androidtest.ViewModal;

import com.example.user.androidtest.Modal.Account;
import com.example.user.androidtest.Modal.Database;
import com.example.user.androidtest.Modal.Person;
import com.example.user.androidtest.Modal.UserType;

import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by User on 3/3/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginViewModalTest {

    @Mock
    Database database;

    @Mock
    LoginViewModal loginViewModal;

    @Before
    public void setup()
    {
        loginViewModal=LoginViewModal.getInstance();
    }

    @Test
    public void getInstance() throws Exception {
        //we instantiate before runnig test
        assertNotNull(loginViewModal);
    }
    @Test
    public void testValidateLogin() throws Exception {


        String email="asd@hotmail.com";
        String pass="123456789!@#";

        when(database.getAccount(new Account(email,pass))).thenReturn(new Account(email,pass));
        when(loginViewModal.isValidEmail(email)).thenReturn(true);
        when(loginViewModal.isValidPassword(pass)).thenReturn(true);

        when(loginViewModal.isValidInput(email,pass)).thenReturn(true);

        //correct input
        loginViewModal.validateLogin("asd@hotmail.com","123456789!@#");
        //verification
        verify(loginViewModal).validateLogin("asd@hotmail.com","123456789!@#");
        assertThat(loginViewModal.getErrorMessages(), is(new HashMap<ErrorType, String>()));

        assertNotNull(loginViewModal);
        assertNotNull(loginViewModal.getAccount());

        //wrong username
        loginViewModal.validateLogin("asdasd","132456789!@#");
        verify(loginViewModal).validateLogin("asdasd","132456789!@#");
        assertThat(loginViewModal.getErrorMessages(),hasKey(ErrorType.Username));

        loginViewModal.getErrorMessages().clear();
        verify(loginViewModal).getErrorMessages().clear();







        loginViewModal.validateLogin("asdadsa@hotmail.com","13245");
        //assertEquals(false,loginViewModal.getErrorMessages());
        loginViewModal.getErrorMessages().clear();
    }

    @Test
    public void register() throws Exception
    {

    }



    @Test
    public void getUserType() throws Exception {

    }

    @Test
    public void getAccount() throws Exception {

    }

    @Test
    public void updatePhoneNumber() throws Exception {
    }



}