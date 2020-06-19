package com.example.robmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
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

import java.time.Year;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    //declaring variables
    private DBHelper dbHelper;
    private Button date,time;
    private EditText task;
    private TextView error;
    private Vibrator v;
    private String yearString;
    private String monthString;
    private String dayString;
    private String hourString;
    private String minuteString;
    private NotificationManagerCompat notificationManagerCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        //creating an object from DBHelper
        dbHelper = new DBHelper(this);

        //assign add editText to task variable
        task = findViewById(R.id.Add_editText);
        error = findViewById(R.id.errorMessage);
        date = findViewById(R.id.dateButton);
        time = findViewById(R.id.timeButton);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int YEAR = calendar.get(calendar.YEAR);
                int MONTH = calendar.get(calendar.MONTH);
//                MONTH++;
                int DATE = calendar.get(calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        yearString = String.valueOf(year);
                        month++;
                        monthString = String.valueOf(month);
                        dayString = String.valueOf(day);
                    }
                }, YEAR, MONTH, DATE);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();

                int HOUR = calendar.get(calendar.HOUR);
                int MIN = calendar.get(calendar.MINUTE);
                Boolean is24HourFormat = DateFormat.is24HourFormat(AddTaskActivity.this);


                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

    //set save icon to toolBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save,menu);
        return true;
    }


    //when user select save button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.save:

                //assign user value to newTask variable
                String newTask = task.getText().toString();

                //validate user input
                if(task.length() == 0){

                    //if user value is generate a error message to user
                    error.setText("Task name can not be empty!");
                    Vibrate();
                }else if(task.length() > 20){

                    //if user value greater than generate a error message to user
                    error.setText("Task name must be less than 20 characters");
                    Vibrate();
                }else if(yearString == null){

                    //checking conditions
                    error.setText("You must select date");
                    Vibrate();
                }else if(hourString == null){

                    //checking conditions
                    error.setText("You must select time");
                    Vibrate();
                }else {

                    //if this is true then insert into database
                    dbHelper.addInfo(newTask,yearString,monthString,dayString,hourString,minuteString);

                    //user will redirect to TaskActivity
                    Intent intent = new Intent(AddTaskActivity.this,TaskActivity.class);
                    startActivity(intent);

                    //print a toast message to indicate user
                    toastMessage("Successfully added");

                    //send notification
                    sendNotification(newTask,yearString,monthString,dayString,hourString,minuteString);
                }
                return true;
        }

        return false;

    }

    //Customized toast function
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    //Vibrate function
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

    //Notification function
    public void sendNotification(String taskName,String year,String month,String day,String hour,String minute){
        Notification notification = new NotificationCompat.Builder(this,App.ch1)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("You got a new task called " + taskName + " on " + year + "." + month + "." + day + " at " + hour + ":" +  minute)
                .setContentText("You can totally do this!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1,notification);
    }

}
