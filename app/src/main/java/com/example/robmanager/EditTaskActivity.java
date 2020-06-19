package com.example.robmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.robmanager.TaskDatabase.DBHelper;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {

    //declaring variables
    private String taskName,toUpdate,year,month,day,hour,minute;
    private int taskID;
    private EditText editText;
    private DBHelper dbHelper;
    private TextView error;
    private AlertDialog.Builder alert;
    private Button dateBtn,timeBtn;
    private Vibrator v;

    private String yearString;
    private String monthString;
    private String dayString;
    private String hourString;
    private String minuteString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        //creating an object from DBHelper
        dbHelper =  new DBHelper(this);

        //get all data from Intent
        taskName = getIntent().getStringExtra("taskName");
        year = getIntent().getStringExtra("taskYear");
        month = getIntent().getStringExtra("taskMonth");
        day = getIntent().getStringExtra("taskDay");
        hour = getIntent().getStringExtra("taskHour");
        minute = getIntent().getStringExtra("taskMinute");
        taskID = getIntent().getIntExtra("taskId",-1);


        //assign editText to a variable
        editText = findViewById(R.id.Edit_editText);
        error = findViewById(R.id.errorMessage);
        dateBtn = findViewById(R.id.editDateValue);
        timeBtn = findViewById(R.id.editTimeValue);

        //set task name to editText
        editText.setText(taskName);

        yearString = String.valueOf(year);
        monthString = String.valueOf(month);
        dayString = String.valueOf(day);

        hourString = String.valueOf(hour);
        minuteString = String.valueOf(minute);


        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int YEAR = Integer.parseInt(year);
                int MONTH = Integer.parseInt(month);
                MONTH--;
                int DATE = Integer.parseInt(day);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        month++;
                        yearString = String.valueOf(year);
                        monthString = String.valueOf(month);
                        dayString = String.valueOf(day);

                    }
                }, YEAR, MONTH, DATE);
                datePickerDialog.show();
            }
        });

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();

                int HOUR = Integer.parseInt(hour);
                int MIN = Integer.parseInt(minute);

                Boolean is24HourFormat = DateFormat.is24HourFormat(EditTaskActivity.this);


                TimePickerDialog timePickerDialog = new TimePickerDialog(EditTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {

                        hourString = String.valueOf(hour);
                        minuteString = String.valueOf(minute);

                    }
                },HOUR, MIN, is24HourFormat);
                timePickerDialog.show();
            }
        });


    }

    //set all icons relate to editTaskActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_task,menu);
        return true;
    }

    //when user select an icon
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                //when user select save icon and get user input and assign to a variable
                toUpdate = editText.getText().toString();

                //check if newTask's length is 0  or not
                if(toUpdate.length() == 0){

                    //if user value less than 1 generate a error message to user
                    error.setText("Task name can not be empty!");
                    Vibrate();
                }else if(toUpdate.length() > 20){

                    //if user value less than 1 generate a error message to user
                    error.setText("Task name must be less than 20 characters");
                    Vibrate();
                }else if(yearString == null){

                    //if user value less than 1 generate a error message to user
                    error.setText("You must select date");
                    Vibrate();
                }else if(hourString == null){

                    //if user value less than 1 generate a error message to user
                    error.setText("You must select time");
                    Vibrate();
                }else {

                    //then insert that value with name and id
                    int res = dbHelper.update(String.valueOf(taskID),toUpdate,yearString,monthString,dayString,hourString,minuteString);

                    //check weather it is updated or not
                    if(res > 0){

                        //if it is updated then print a message with Toast
                        toastMessage("Successfully updated");

                        //redirect user to TaskActivity
                        Intent intent = new Intent(EditTaskActivity.this,TaskActivity.class);
                        intent.putExtra("taskName",toUpdate);
                        intent.putExtra("taskId",taskID);
                        startActivity(intent);
                    }

                }

                return true;

            case R.id.delete:

                alert = new AlertDialog.Builder(this);
                alert.setTitle("Delete Alert");
                alert.setMessage("Do you want to delete this task ?");

                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //when user select delete icon then that task will be removed at this point
                        dbHelper.deleteTask(taskName);

                        //then redirect user to TaskActivity
                        Intent i = new Intent(EditTaskActivity.this,TaskActivity.class);
                        startActivity(i);

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });

                alert.show();

                return true;
        }
        return false;
    }

    //Customized Toast function
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void Vibrate(){
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

}
