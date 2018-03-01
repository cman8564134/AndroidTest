package com.example.user.androidtest.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.androidtest.Interface.SignUpView;
import com.example.user.androidtest.R;
import com.example.user.androidtest.ViewModal.LoginViewModal;
import com.example.user.androidtest.ViewModal.SignUpViewModal;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements SignUpView{

    Spinner userType;
    EditText firstName;
    EditText lastName;
    EditText phoneNumber;
    private LoginViewModal signUpViewModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //revert to normal theme after done loading
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        initViewModal();
    }

    private void initView()
    {
        firstName=(EditText) findViewById(R.id.FirstNameText);
        lastName = (EditText) findViewById(R.id.LastNameText);
        phoneNumber=(EditText) findViewById(R.id.PhoneNumberText);
        findViewById(R.id.SignUpButton).setOnClickListener((View.OnClickListener) this);

        userType= (Spinner) findViewById(R.id.userTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.userTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter);

    }
    private void initViewModal()
    {
        signUpViewModal= ViewModelProviders.of(this).get(LoginViewModal.class);
    }

    public void onClick(View v) {
        if(isValidForm())
            signUpViewModal.validateRegister(userType.getSelectedItem().toString(),firstName.getText().toString(),lastName.getText().toString(),phoneNumber.getText().toString());
    }

    private boolean isValidForm()
    {
        if(     firstName.getText().toString().trim().isEmpty()||
                lastName.getText().toString().trim().isEmpty()||
                phoneNumber.getText().toString().trim().isEmpty()||
                userType.getSelectedItem().toString().isEmpty())
        {
            Snackbar.make(findViewById(R.id.SignUpParentLayout), "Please Complete The Form!", Snackbar.LENGTH_SHORT);
            return false;
        }
        return true;

    }

    @Override
    public void setError(String type, String message) {

    }

    private void userTypeError(String message)
    {
        ((TextView)userType.getSelectedView()).setError(message);
    }

    @Override
    public void startHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finishAffinity();

    }
}
