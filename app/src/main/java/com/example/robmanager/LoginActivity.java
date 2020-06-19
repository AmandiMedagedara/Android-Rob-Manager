package com.example.robmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.robmanager.UserDatabase.User;
import com.example.robmanager.UserDatabase.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;


public class LoginActivity extends AppCompatActivity {

    //Declaration EditTexts
    EditText email;
    EditText password;



    //Declaration Button
    Button button5;
    Button button6;


    //Declaration SqliteHelper
    DatabaseHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sqliteHelper = new DatabaseHelper(this);
        //initCreateAccountTextView();
        initViews();

        //set click event of login button
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check user input is correct or not
                if (validate()) {

                    //Get values from EditText fields
                    String Email =email.getText().toString();
                    String Password = password.getText().toString();

                    //Authenticate user
                    User currentUser = sqliteHelper.Authenticate(new User(null, null, Email, Password));

                    //Check Authentication is successful or not
                    if (currentUser != null) {
                        Snackbar.make(button6, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);

                        //User Logged in Successfully Launch You home screen activity
//                       Intent intent=new Intent(LoginActivity.this,HomeScreenActivity.class);
//                        startActivity(intent);
//                        finish();
                    } else {

                        //User Logged in Failed
                        Snackbar.make(button6, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });


    }



    //this method is used to connect XML views to its Objects
    private void initViews() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        button6 = (Button) findViewById(R.id.button6);

    }

    //This method is for handling fromHtml method deprecation
//    @SuppressWarnings("deprecation")
//    public static Spanned fromHtml(String html) {
//        Spanned result;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
//        } else {
//            result = Html.fromHtml(html);
//        }
//        return result;
//    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        //Handling validation for Email field
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
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
