package com.example.user.androidtest.ViewModal;

import com.example.user.androidtest.Modal.Account;
import com.example.user.androidtest.Modal.UserType;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by User on 3/3/2018.
 */

//unit testing for Viewmodal class but only tested whether the actual function is called in the object because Mockito does not support static classes
@RunWith(MockitoJUnitRunner.class)
public class LoginViewModalTest {

    @Mock
    static LoginViewModal loginViewModal=LoginViewModal.getInstance();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();



    @Test
    public void getInstance() throws Exception {
        //we instantiate before runnig test
        assertNotNull(loginViewModal);
    }
    @Test
    public void testValidateLogin() throws Exception {
        //input
        String email="asd@hotmail.com";
        String pass="123456789!@#";

        loginViewModal.validateLogin(email,pass);
        //verification
        verify(loginViewModal).validateLogin(email,pass);
        assertThat(loginViewModal.getErrorMessages(), is(new HashMap<ErrorType, String>()));


    }

    @Test
    public void register() throws Exception
    {
        String type=UserType.Private.name();
        String firstname="asd";
        String lastName="fgh";
        String phoneNumber= "0123456789";

        loginViewModal.Register(type,firstname,lastName,phoneNumber);
        //verification
        verify(loginViewModal).Register(type,firstname,lastName,phoneNumber);


    }


    @Test
    public void testGetterSetterAccount() throws Exception {
        Account a = new Account("asd@hot.com","123456789!@#");

        assertNull(loginViewModal.getAccount());
        loginViewModal.setAccount(a);
        verify(loginViewModal).setAccount(a);

    }

    @Test
    public void updatePhoneNumber() throws Exception {

        String phoneno="0123456789";
        loginViewModal.updatePhoneNumber(phoneno);
        verify(loginViewModal).updatePhoneNumber(phoneno);
    }

    @Test
    public void testCreatePersonObject() throws Exception
    {
        UserType type=UserType.Private;
        String firstname="asd";
        String lastName="fgh";
        String phoneNumber= "0123456789";

        loginViewModal.createPersonObject(firstname,lastName,type.name(),phoneNumber);
        verify(loginViewModal).createPersonObject(firstname,lastName,type.name(),phoneNumber);
    }

    @Test
    public void testCheckDatabase() throws  Exception
    {

        Account a = new Account("asd@hot.com","123456789!@#");
        loginViewModal.checkDatabase(a);
        verify(loginViewModal).checkDatabase(a);
    }

    @Test
    public void testIsValidInput() throws Exception
    {
        String email="asd@hotmail.com";
        String pass="123456789!@#";
        loginViewModal.isValidInput(email,pass);
        verify(loginViewModal).isValidInput(email,pass);
    }

    @Test
    public void testIsValidPassword() throws Exception
    {

        String pass="123456789!@#";
        loginViewModal.isValidPassword(pass);
        verify(loginViewModal).isValidPassword(pass);
    }

    @Test
    public void testIsValidPattern() throws Exception
    {

        String pass="123456789!@#";
        loginViewModal.isValidPattern(pass);
        verify(loginViewModal).isValidPattern(pass);
    }

    @Test
    public void testIsValidEmail() throws Exception
    {

        String email="asd@hotmail.com";
        loginViewModal.isValidEmail(email);
        verify(loginViewModal).isValidEmail(email);
    }

    @Test
    public void testIsValidPhoneno() throws Exception
    {


        String no="0123456789";
        loginViewModal.isValidEmail(no);
        verify(loginViewModal).isValidEmail(no);
    }

    @Test
    public void testSetError() throws Exception
    {
        loginViewModal.setError(ErrorType.UserType,"error message");
        verify(loginViewModal).setError(ErrorType.UserType,"error message");
    }


}