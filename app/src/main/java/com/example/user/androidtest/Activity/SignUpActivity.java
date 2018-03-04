package com.example.user.androidtest.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.androidtest.R;
import com.example.user.androidtest.ViewModal.ErrorType;
import com.example.user.androidtest.ViewModal.LoginViewModal;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    Spinner userType;
    EditText firstName;
    EditText lastName;
    EditText phoneNumber;
    private LoginViewModal loginViewModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //revert to normal theme after done loading
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViewModal();
        initView();
    }

    private void initView()
    {
        firstName=(EditText) findViewById(R.id.FirstNameText);
        lastName = (EditText) findViewById(R.id.LastNameText);
        phoneNumber=(EditText) findViewById(R.id.PhoneNumberText);
        findViewById(R.id.SignUpButton).setOnClickListener(this);

        userType= (Spinner) findViewById(R.id.userTypeSpinner);
        userType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loginViewModal.getUserType()));
        firstName.requestFocus();

    }

    private void initViewModal()
    {
        loginViewModal = LoginViewModal.getInstance();
    }

    public void onClick(View v) {

        if(isValidForm()) {
            System.out.println("OnClick+isValidForm()");
            System.out.println(userType.getSelectedItem().toString()+firstName.getText().toString()+lastName.getText().toString()+ phoneNumber.getText().toString());
            loginViewModal.Register(userType.getSelectedItem().toString().trim(), firstName.getText().toString().trim(), lastName.getText().toString().trim(), phoneNumber.getText().toString().trim());

            if (isErrorFree()) {
                //cache user information
                storeToSharedPreference(new String[]{loginViewModal.getAccount().getID(),loginViewModal.getAccount().getPassword()});
                //navigate to Homepage
                startHomeActivity();
            }

        }
    }

    private boolean isValidForm()
    {
        if(     firstName.getText().toString().trim().isEmpty()||
                lastName.getText().toString().trim().isEmpty()||
                phoneNumber.getText().toString().trim().isEmpty()||
                userType.getSelectedItem().toString().isEmpty())
        {
            Snackbar.make(findViewById(R.id.SignUpParentLayout), "Please Complete The Form!", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void storeToSharedPreference(String[] account)
    {
        SharedPreferences prefs = getSharedPreferences("UserAccountData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", account[0]);
        editor.putString("password", account[1]);
        editor.commit();
    }

   public void setError(ErrorType type, String message) {
        if(type.equals(ErrorType.PhoneNo))
        {
            setPhoneNoError(message);
        }
        else if(type.equals(ErrorType.UserType))
        {
            setUserTypeError(message);
        }
    }

    private void setPhoneNoError(String message)
    {
        phoneNumber.requestFocus();
        phoneNumber.setError(message);
    }

    private void setUserTypeError(String message)
    {   userType.requestFocus();
        ((TextView)userType.getSelectedView()).setError(message);
    }


    public void startHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finishAffinity();
    }

    private boolean isErrorFree()
    {
        if(!loginViewModal.getErrorMessages().isEmpty())
        {
            for (Map.Entry<ErrorType,String> entry : loginViewModal.getErrorMessages().entrySet()) {
                setError(entry.getKey(),entry.getValue());
            }

            loginViewModal.getErrorMessages().clear();
            return false;
        }
        return true;

    }
}
