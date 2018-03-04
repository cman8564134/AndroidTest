package com.example.user.androidtest;


import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.user.androidtest.Activity.HomeActivity;
import com.example.user.androidtest.Activity.LoginMainActivity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by User on 3/3/2018.
 */

//instrumental test
@RunWith(AndroidJUnit4.class)
public class LoginUITest {


    @Rule
    public ActivityTestRule<LoginMainActivity> mActivityRule =
            new ActivityTestRule<>(LoginMainActivity.class);

    //test when both field empty in login form
    @Test
    public void ensureFormValidationWork()
    {
        onView(withId(R.id.loginButton)).perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Please Complete The Form!")))
                .check(matches(isDisplayed()));
    }

    //test when password field empty in login form
    @Test
    public void ensureFormValidationWork2()
    {
        onView(withId(R.id.loginText)).perform(typeText("asd"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Please Complete The Form!")))
                .check(matches(isDisplayed()));
    }

    //test when ID field empty in login form
    @Test
    public void ensureFormValidationWork3()
    {
        onView(withId(R.id.passText)).perform(typeText("asd"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Please Complete The Form!")))
                .check(matches(isDisplayed()));
    }

    //test to ensure Email Validation works
    @Test
    public void ensureEmailValidationWorks()
    {
        onView(withId(R.id.loginText)).perform(typeText("asd"));
        onView(withId(R.id.passText)).perform(typeText("123456789!@#"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginText)).check(matches(hasErrorText("Email is not valid")));
    }

    //test to ensure Email Validation works
    @Test
    public void ensureEmailValidationWorks2()
    {
        onView(withId(R.id.loginText)).perform(typeText("asd@hot"));
        onView(withId(R.id.passText)).perform(typeText("123456789!@#"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginText)).check(matches(hasErrorText("Email is not valid")));
    }

    //test to ensure password Validation works
    @Test
    public void ensurePasswordValidationWorks()
    {
        onView(withId(R.id.loginText)).perform(typeText("asd@hot.com"));
        onView(withId(R.id.passText)).perform(typeText("asd"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.passText)).check(matches(hasErrorText("Password should contain one special character and minimum 8 characters required")));
    }

    //test to ensure password Validation works
    @Test
    public void ensurePasswordValidationWorks2()
    {
        onView(withId(R.id.loginText)).perform(typeText("asd@hot.com"));
        onView(withId(R.id.passText)).perform(typeText("123456789"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.passText)).check(matches(hasErrorText("Password should contain one special character and minimum 8 characters required")));
    }

    //test to ensure login works
    @Test
    public void sucessfulNavigation()
    {
        onView(withId(R.id.loginText)).perform(typeText("asd@gman.com"));
        onView(withId(R.id.passText)).perform(typeText("123456789!@#"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.SignUpButton)).check(matches(withText("SignUp")));
    }



}
