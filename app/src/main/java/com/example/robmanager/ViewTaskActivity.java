package com.example.robmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTaskActivity extends AppCompatActivity {

    private String taskName,year,month,day,hour,minute;
    private int taskId;
    private TextView value,dateValue,timeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        taskName = getIntent().getStringExtra("taskName");
        taskId = getIntent().getIntExtra("taskId",-1);
        year = getIntent().getStringExtra("taskYear");
        month = getIntent().getStringExtra("taskMonth");
        day = getIntent().getStringExtra("taskDay");
        hour = getIntent().getStringExtra("taskHour");
        minute = getIntent().getStringExtra("taskMinute");
        value = findViewById(R.id.taskValue);
        dateValue = findViewById(R.id.dateValue);
        timeValue = findViewById(R.id.timeValue);

        value.setText(taskName);
        dateValue.setText(year + "." + month + "." + day);
        timeValue.setText(hour + ":" + minute);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_task,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.editPen:
                Intent intent = new Intent(ViewTaskActivity.this,EditTaskActivity.class);
                intent.putExtra("taskName",taskName);
                intent.putExtra("taskId",taskId);
                intent.putExtra("taskYear",year);
                intent.putExtra("taskMonth",month);
                intent.putExtra("taskDay",day);
                intent.putExtra("taskHour",hour);
                intent.putExtra("taskMinute",minute);
                startActivity(intent);
                Toast.makeText(this,"Edit Pen",Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}
