package com.example.robmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class intro3 extends AppCompatActivity {

    Button skip,cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        skip = findViewById(R.id.button10);
        cont = findViewById(R.id.button11);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(intro3.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(intro3.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
