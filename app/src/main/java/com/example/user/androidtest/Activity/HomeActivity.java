package com.example.user.androidtest.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.androidtest.Modal.Account;
import com.example.user.androidtest.R;
import com.example.user.androidtest.ViewModal.ErrorType;
import com.example.user.androidtest.ViewModal.LoginViewModal;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    LoginViewModal loginViewModal;
    private TextView email;
    private TextView firstName;
    private TextView lastName;
    private TextView phoneNo;
    private Button signOutButton;
    private Button userTypeButton;
    private Button editPhoneNoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //revert to normal theme after done loading
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViewModal();

        if(!validateLogin(getUserAccountData())) {
            startLoginActivity();
        }

        else {
            initView();
            //set values
            updateUIValues();
        }

    }

    private void initView()
    {
        email=(TextView) findViewById(R.id.emailText);
        firstName= (TextView) findViewById(R.id.firstNameText);
        lastName= (TextView) findViewById(R.id.lastNameText);
        phoneNo= (TextView) findViewById(R.id.phoneNumberText);
        findViewById(R.id.editPhoneNumberButton).setOnClickListener(this);
        findViewById(R.id.LogoutButton).setOnClickListener(this);
        findViewById(R.id.userTypeButton).setOnClickListener(this);

    }

    private void initViewModal()
    {
        loginViewModal= LoginViewModal.getInstance();
    }

    private Account getUserAccountData()
    {
        SharedPreferences prefs = getSharedPreferences("UserAccountData", MODE_PRIVATE);
        if(prefs.getString("username",null)!=null)
        {
            String username = prefs.getString("username","");
            String pwd = prefs.getString("password","");
            return new Account(username,pwd);
        }
        return null;

    }

    private void updateUIValues()
    {
        email.setText(loginViewModal.getAccount().getID());
        firstName.setText(loginViewModal.getAccount().getUser().getFirstName());
        lastName.setText(loginViewModal.getAccount().getUser().getLastName());
        phoneNo.setText(loginViewModal.getAccount().getUser().getPhoneNo());
    }
    private void startLoginActivity()
    {
        //login redirect
        startActivity(new Intent(this, LoginMainActivity.class));

        finish();
    }

    private boolean validateLogin(Account account)
    {
        if(account!=null) {
            loginViewModal.validateLogin(account.getID(), account.getPassword());

            if(isErrorFree())
                return true;
        }
        return false;
    }


    private void setError(ErrorType inputType, String message)
    {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void createSignOutDialog() {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Do you really want to sign out?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeAccountData();
                        restartApp();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            private static final int AUTO_DISMISS_MILLIS = 6000;
            @Override
            public void onShow(final DialogInterface dialog) {
                final Button defaultButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                final CharSequence positiveButtonText = defaultButton.getText();
                new CountDownTimer(AUTO_DISMISS_MILLIS, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        defaultButton.setText(String.format(
                                Locale.getDefault(), "%s (%d)",
                                positiveButtonText,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1 //add one so it never displays zero
                        ));
                    }
                    @Override
                    public void onFinish() {
                        if (((AlertDialog) dialog).isShowing()) {
                            dialog.dismiss();
                            removeAccountData();
                            restartApp();
                        }
                    }
                }.start();
            }
        });
        dialog.show();
    }

    private void restartApp()
    {
        try {
            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        catch (Exception e)
        {
            System.out.println("Exception received:"+e);
        }
        finish();
    }

    private void removeAccountData()
    {

        SharedPreferences preferences =getSharedPreferences("UserAccountData",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    private void showEditPhoneDialogBox()
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Phone Number");
        alertDialog.setMessage("Enter new phone number");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_action_phone);

        alertDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        loginViewModal.updatePhoneNumber(input.getText().toString().trim());
                        if(isErrorFree())
                            updateUIValues();
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
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




    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.userTypeButton:
                Toast.makeText(this, "Your account type is: "+loginViewModal.getAccount().getUser().getType().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.editPhoneNumberButton:
                showEditPhoneDialogBox();
                break;

            case R.id.LogoutButton:
                createSignOutDialog();
                break;

            default:
                break;
        }

    }
}
