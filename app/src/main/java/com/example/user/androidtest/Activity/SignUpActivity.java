package com.example.user.androidtest.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.user.androidtest.R;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    Spinner userType;
    EditText firstName;
    EditText lastName;
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    private void initViews()
    {
        firstName=(EditText) findViewById(R.id.FirstNameText);
        lastName = (EditText) findViewById(R.id.LastNameText);
        phoneNumber=(EditText) findViewById(R.id.PhoneNumberText);
        userType= (Spinner) findViewById(R.id.userTypeSpinner);

        ArrayAdapter.createFromResource(this,
                data, android.R.layout.simple_spinner_item);
    }
    public void updateSpinner(ArrayList data)
    {
        ArrayAdapter.createFromResource(this,
                data, android.R.layout.simple_spinner_item);
    }
}
