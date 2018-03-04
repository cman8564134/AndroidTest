package com.example.user.androidtest.ViewModal;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.databinding.BaseObservable;
import android.provider.ContactsContract;
import android.support.annotation.VisibleForTesting;
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
    private HashMap<ErrorType,String> errorMessages=new HashMap<>();
    private Database database= new Database();

    @VisibleForTesting
    protected LoginViewModal(){}


    public static LoginViewModal getInstance(){

        if (loginViewModal == null) {
            loginViewModal = new LoginViewModal();
        }
        return loginViewModal;
    }



    public void validateLogin(String username,String password){

        if(isValidInput(username,password))
        {
            setAccount(new Account(username,password));
            checkDatabase(account);
        }
    }

    public void Register(String userType,String firstName, String lastName, String phoneNumber){
        if(isValidPhoneNumber(phoneNumber))
        {
            Person p= createPersonObject(userType,firstName,lastName,phoneNumber);

            if (p!=null) {
                System.out.println("Account is:"+account);
                account.setUser(p);
                this.database.registerOrUpdateAccount(account);
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

    public void setAccount (Account account) {this.account=account;}
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
            this.database.registerOrUpdateAccount(account);
        }

    }

    public HashMap<ErrorType, String> getErrorMessages() {
        return errorMessages;
    }

    //codes after this should be made private but for unit testing purpose is made public

    @VisibleForTesting
    Person createPersonObject(String userType,String firstName,String lastName,String phoneNumber)
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


    @VisibleForTesting
    void checkDatabase(Account acc)
    {

        Account specificPerson =this.database.getAccount(acc);

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

    @VisibleForTesting
    boolean isValidInput(String username,String password)
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

    @VisibleForTesting
    boolean isValidPassword(String password)
    {
        if(password.length()<8||!isValidPattern(password))
            return false;
        else
            return true;

    }
    @VisibleForTesting
    boolean isValidPattern(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @VisibleForTesting
    boolean isValidEmail(CharSequence target) {
        if (target!=null)
            return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
        else
            return false;
    }

    @VisibleForTesting
    void setError(ErrorType type, String message) {
        errorMessages.put(type,message);

    }

    @VisibleForTesting
    boolean isValidPhoneNumber(String phonenumber)
    {
        if(!Patterns.PHONE.matcher(phonenumber).matches()||phonenumber.length()!=10||!phonenumber.startsWith("01")) {
            setError(ErrorType.PhoneNo,"Phone Number Not valid");
            return false;
        }
        else
            return true;

    }

}
