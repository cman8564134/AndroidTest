package com.example.user.androidtest.ViewModal;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Switch;

import com.example.user.androidtest.Activity.SignUpActivity;
import com.example.user.androidtest.Modal.Account;
import com.example.user.androidtest.Modal.Database;
import com.example.user.androidtest.Modal.Person;
import com.example.user.androidtest.Modal.UserType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by User on 26/2/2018.
 */


public class LoginViewModal {

    //implement singleton to ensure data sharing with activities
    private static LoginViewModal loginViewModal;
    private Account account;
    private final String passwordErrorMessage="Password should contain one special character and minimum 8 characters required";
    private final String IDErrorMessage="Email is not valid";
    private static HashMap<ErrorType,String> errorMessages;

    private LoginViewModal(){}
    public static LoginViewModal getInstance()
    {

        if (loginViewModal == null) {
            errorMessages=new HashMap<>();
            loginViewModal = new LoginViewModal();
        }
        return loginViewModal;
    }

    public void validateLogin(String username,String password)
    {

        if(isValidInput(username,password))
        {
            account=new Account(username,password);
            checkDatabase(account);
        }
    }

    public void Register(String userType,String firstName, String lastName, String phoneNumber)
    {
        if(isValidPhoneNumber(phoneNumber))
        {
            Person p= createPersonObject(userType,firstName,lastName,phoneNumber);

            if (p!=null) {
                System.out.println("Account is:"+account);
                account.setUser(p);
                getDatabaseInstance().registerOrUpdateAccount(account);
                return;
            }
            else
                setError(ErrorType.UserType, "Please choose one of the selected user type");

        }

    }

    public UserType[] getUserType()
    {
        return UserType.values();
    }

    public Account getAccount()
    {
        return this.account;
    }

    public void updatePhoneNumber(String phoneNo)
    {
        if(isValidPhoneNumber(phoneNo))
        {
            //actually this function can be used as update account details
            account.setUser(new Person(account.getUser().getFirstName(),account.getUser().getLastName(),account.getUser().getType(),phoneNo));
            getDatabaseInstance().registerOrUpdateAccount(account);
        }

    }

    public HashMap<ErrorType, String> getErrorMessages() {
        return errorMessages;
    }

    //codes after this should be made private but for unit testing purpose is made public

    public Person createPersonObject(String userType,String firstName,String lastName,String phoneNumber)
    {
        Person p;
        switch(userType)
        {
            case "Agent": p= new Person(firstName,lastName,UserType.Agent,phoneNumber);
            break;
            case "Broker": p= new Person(firstName,lastName,UserType.Broker,phoneNumber);
            break;
            case "Dealer": p= new Person(firstName,lastName,UserType.Dealer,phoneNumber);
            break;
            case "Private": p= new Person(firstName,lastName,UserType.Private,phoneNumber);
            break;
            default: p= null;
            break;
        }
        return p;
    }



    private void checkDatabase(Account acc)
    {

        Account specificPerson =getDatabaseInstance().getAccount(acc);

        if(specificPerson!=null) {
            //existing ID
            //check password
            if(!specificPerson.getPassword().equals(acc.getPassword())) {
                setError(ErrorType.Password, "Wrong Password");
                return;
            }
            else
                account.setUser(specificPerson.getUser());
        }
        else
        {
            //new ID
            //the person is still null but we set it to double confirm
            account.setUser(null);

        }

    }


    public boolean isValidInput(String username,String password)
    {

        if(!isValidEmail(username))
        {
            setError(ErrorType.Username,IDErrorMessage);
            return false;
        }
        else if (!isValidPassword(password))
        {
            setError(ErrorType.Password,passwordErrorMessage);
            return false;
        }
        else
            return true;


    }

    public boolean isValidPassword(String password)
    {
        if(password.length()<8)
            return false;
        else if (!isValidPattern(password))
        {
            return false;
        }
        else
            return true;

    }

    private boolean isValidPattern(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean isValidEmail(CharSequence target) {
        if (target!=null)
            return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
        else
            return false;
    }


    private void setError(ErrorType type, String message) {
        errorMessages.put(type,message);

    }






    private boolean isValidPhoneNumber(String phonenumber)
    {
        if(!Patterns.PHONE.matcher(phonenumber).matches()||phonenumber.length()!=10||!phonenumber.startsWith("01")) {
            setError(ErrorType.PhoneNo,"Phone Number Not valid");
            return false;
        }
        else
            return true;

    }



    private Database getDatabaseInstance()
    {
        return Database.getInstance();
    }
}
