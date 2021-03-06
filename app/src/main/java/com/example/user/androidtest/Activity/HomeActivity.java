package com.example.user.androidtest.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.androidtest.R;
import com.example.user.androidtest.ViewModal.ErrorType;
import com.example.user.androidtest.ViewModal.LoginViewModal;

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

    //function to initialise views
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

    //function to initialise the viewmodal
    private void initViewModal()
    {
        loginViewModal= LoginViewModal.getInstance();
    }

    //retrieve account data from shared preferences if exist
    private String[] getUserAccountData()
    {
        SharedPreferences prefs = getSharedPreferences("UserAccountData", MODE_PRIVATE);
        if(prefs.getString("username",null)!=null)
        {
            String username = prefs.getString("username","");
            String pwd = prefs.getString("password","");
            return new String[]{username,pwd};
        }
        return null;

    }

    //update the values in the Activity when there is an update at the repository
    private void updateUIValues()
    {
        email.setText(loginViewModal.getAccount().getID());
        firstName.setText(loginViewModal.getAccount().getUser().getFirstName());
        lastName.setText(loginViewModal.getAccount().getUser().getLastName());
        phoneNo.setText(loginViewModal.getAccount().getUser().getPhoneNo());
    }

    //start login activity if this is new user
    private void startLoginActivity()
    {

        startActivity(new Intent(this, LoginMainActivity.class));
        finish();
    }

    //after getting cached account data, relogin using the saved ID and password
    private boolean validateLogin(String[] account)
    {
        if(account!=null) {
            loginViewModal.validateLogin(account[0], account[1]);

            if(isErrorFree())
                return true;
        }
        return false;
    }


    //show user error message(can be further splitted into error type but it was not needed in this case)
    private void setError(ErrorType inputType, String message)
    {
        showToast(message);
    }

    //create and show dialog box after user click sign out
    private void showSignOutDialog() {

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
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
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

    //used to restart the app after signout (to show the splash screen)
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

    //remove cached account data after sign out so that the same data would not be saved in the cache
    private void removeAccountData()
    {

        SharedPreferences preferences =getSharedPreferences("UserAccountData",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    //show a dialog box for user to edit their phone number
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
                        if(isErrorFree()) {
                            updateUIValues();
                            showSnackBar("Number Edit Successful!");
                        }
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

    //check whether there is an error at the viewmodel (observer replacement)
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
                showToast("Your account type is: "+loginViewModal.getAccount().getUser().getType().toString());
                break;

            case R.id.editPhoneNumberButton:
                showEditPhoneDialogBox();
                break;

            case R.id.LogoutButton:
                showSignOutDialog();
                break;

            default:
                break;
        }

    }

    //utility function to help show toast messages
    public void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //utility function to help show snackbar messages
    public void showSnackBar(String message)
    {

        Snackbar.make(findViewById(R.id.HomeLayout), message, Snackbar.LENGTH_SHORT).show();
    }
}
