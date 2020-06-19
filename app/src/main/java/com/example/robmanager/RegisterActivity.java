package com.example.robmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.robmanager.UserDatabase.User;
import com.example.robmanager.UserDatabase.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

//import android.support.annotation.Nullable;
//import android.support.design.widget.Snackbar;
//import android.support.design.widget.TextInputLayout;
//                       import android.support.v7.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    //Declaration EditTexts
    EditText username;
    EditText email;
    EditText password;



    //Declaration Button
    Button button3;

    //Declaration SqliteHelper
    DatabaseHelper DatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DatabaseHelper = new DatabaseHelper(this);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.username);
        button3 = (Button) findViewById(R.id.button3);
       // initTextViewLogin();
        initViews();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = username.getText().toString();
                    String Email = email.getText().toString();
                    String Password = password.getText().toString();

                    //Check in the database is there any user associated with  this email
                    if (!DatabaseHelper.isEmailExists(Email)) {

                        //Email does not exist now add new user to database
                        DatabaseHelper.addUser(new User(null, UserName, Email, Password));
                        Snackbar.make(button3, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                        intent.putExtra("userName",UserName);
                        intent.putExtra("email",Email);
                        intent.putExtra("password",Password);
                        startActivity(intent);

                    }else {

                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(button3, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    //this method used to set Login TextView click event
   // private void initTextViewLogin() {
    //    TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin)
      //  textViewLogin.setOnClickListener(new View.OnClickListener() {
      //      @Override
            public void onClick(View view) {
//            }
       // });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {

       // textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        //textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        //textInputLayoutUserName = (TextInputLayout) findViewById(R.id.textInputLayoutUserName);


    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = username.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            username.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                username.setError(null);
            } else {
                valid = false;
                username.setError("Username is to short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            email.setError("Please enter valid email!");
        } else {
            valid = true;
            email.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            password.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                password.setError(null);
            } else {
                valid = false;
                password.setError("Password is to short!");
            }
        }


        return valid;
    }
}