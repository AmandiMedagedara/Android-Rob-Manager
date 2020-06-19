package com.example.robmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Intro extends AppCompatActivity {

    Button skip,cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        skip = (Button) findViewById(R.id.button);
        cont = (Button) findViewById(R.id.button2);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intro.this,Intro1.class);
                startActivity(intent1);
            }
        });
    }


}
