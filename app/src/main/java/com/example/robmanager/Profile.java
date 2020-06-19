package com.example.robmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    String un,email,password;
    TextView usernameValue,emailValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        un = getIntent().getStringExtra("userName");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");


        usernameValue = findViewById(R.id.textView14);
        emailValue = findViewById(R.id.textView16);

        usernameValue.setText(un);
        emailValue.setText(email);

    }
}
