package com.example.robmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robmanager.TaskDatabase.DBHelper;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    ListView taskListView;
    DBHelper dbHelper;
    SearchView theFilter;
    ArrayAdapter<String> taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        theFilter = findViewById(R.id.search);

        taskListView = findViewById(R.id.task_listView);
        dbHelper = new DBHelper(this);

        List data = dbHelper.readAllInfo();

        taskAdapter = new ArrayAdapter<String>(this,R.layout.task_layout,R.id.task_textView,data);
        taskListView.setAdapter(taskAdapter);

        if(taskListView.getCount() == 0){
            toastMessage("You can add tasks by pressing + option");
        }

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = parent.getItemAtPosition(position).toString();

                Cursor data = dbHelper.getTaskID(name);

                int taskID = -1;

                while(data.moveToNext()){
                    taskID = data.getInt(0);
                }

                List details = dbHelper.getTaskDetails(taskID);

                if(taskID > -1){
                    Intent intent = new Intent(TaskActivity.this,ViewTaskActivity.class);
                    intent.putExtra("taskName", String.valueOf(details.get(0)));
                    intent.putExtra("taskYear",String.valueOf(details.get(1)));
                    intent.putExtra("taskMonth",String.valueOf(details.get(2)));
                    intent.putExtra("taskDay",String.valueOf(details.get(3)));
                    intent.putExtra("taskHour",String.valueOf(details.get(4)));
                    intent.putExtra("taskMinute",String.valueOf(details.get(5)));
                    intent.putExtra("taskId",taskID);
                    startActivity(intent);
                }else {
                    toastMessage("There is an error!");
                }

            }
        });

        registerForContextMenu(taskListView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_menu,menu);

        MenuItem searchTask = menu.findItem(R.id.search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchTask.getActionView();

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                taskAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(TaskActivity.this,AddTaskActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
