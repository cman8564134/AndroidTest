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

//the intermediatory between the views and the models
    //holds all the functional logic of the application
public class LoginViewModal {


    private static LoginViewModal loginViewModal;
    private Account account;
    private final String passwordErrorMessage="Password should contain one special character and minimum 8 characters required";
    private final String IDErrorMessage="Email is not valid";
    private HashMap<ErrorType,String> errorMessages=new HashMap<>();
    private Database database= new Database();


    protected LoginViewModal(){}

    //implement singleton to ensure data sharing between activities
    public static LoginViewModal getInstance(){

        if (loginViewModal == null) {
            loginViewModal = new LoginViewModal();
        }
        return loginViewModal;
    }



    //validate the login by validating input, checking the database for existing users and comparing the account details such as ID and password
    public void validateLogin(String username,String password){

        if(isValidInput(username,password))
        {
            setAccount(new Account(username,password));
            checkDatabase(account);
        }
    }

    //register the user with the data provided by user after validating it
    public void Register(String userType,String firstName, String lastName, String phoneNumber){

        //fail save: incase user is able to enter registration without going through login
        if(account!=null&&account.getID()!=null) {
            //cehck whether phone number is valid
            if (isValidPhoneNumber(phoneNumber)) {
                Person p = createPersonObject(userType, firstName, lastName, phoneNumber);

                if (p != null) {
                    System.out.println("Account is:" + account);
                    account.setUser(p);
                    this.database.registerOrUpdateAccount(account);
                    return;
                }
                else
                    setError(ErrorType.UserType, "Please choose one of the selected user type");

            }
        }
        else
            setError(ErrorType.Username, "Account Information in incomplete");

    }

    //returns the values of the possible user type to fill in data for spinner
    public UserType[] getUserType()
    {
        return UserType.values();
    }

    public void setAccount (Account account) {this.account=account;}
    public Account getAccount()
    {
        return this.account;
    }

    //update user's phone number after validating it
    public void updatePhoneNumber(String phoneNo)
    {
        if(isValidPhoneNumber(phoneNo))
        {

            account.setUser(new Person(account.getUser().getFirstName(),account.getUser().getLastName(),account.getUser().getType(),phoneNo));
            this.database.registerOrUpdateAccount(account);
        }

    }


    public HashMap<ErrorType, String> getErrorMessages() {
        return errorMessages;
    }



    //create a person object whenever neccessary eg signup/login
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


    //check database whether it has existing account and compare the account details
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

    //validate the account details
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

    //password validation
    @VisibleForTesting
    boolean isValidPassword(String password)
    {
        if(password.length()<8||!isValidPattern(password))
            return false;
        else
            return true;

    }

    //password pattern validation eg 8 character with special character included
    @VisibleForTesting
    boolean isValidPattern(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    //validate email
    @VisibleForTesting
    boolean isValidEmail(CharSequence target) {
        if (target!=null)
            return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
        else
            return false;
    }

    //compile error messages so that UI can retrieve after execution (observer replacement)
    @VisibleForTesting
    void setError(ErrorType type, String message) {
        errorMessages.put(type,message);

    }

    //phone number validation
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
