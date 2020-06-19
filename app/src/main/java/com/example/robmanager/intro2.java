package com.example.robmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class intro2 extends AppCompatActivity {

    Button skip,cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        skip = findViewById(R.id.button8);
        cont = findViewById(R.id.button9);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(intro2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(intro2.this,intro3.class);
                startActivity(intent1);
            }
        });
    }
}
