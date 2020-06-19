package com.example.robmanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.robmanager.BudgetDatabase.DBHelper;

import java.util.List;

public class Budget extends AppCompatActivity {

    ListView budgetListView;
    DBHelper dbHelper;
    ArrayAdapter<String> budgetAdapter;
    SearchView theFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        budgetListView = findViewById(R.id.budget_listView);
        dbHelper = new DBHelper(this);

        List data = dbHelper.readAllInfo();


        budgetAdapter = new ArrayAdapter<String>(this, R.layout.budget_layout, R.id.budget_textview,data);
        budgetListView.setAdapter(budgetAdapter);

        registerForContextMenu(budgetListView);

        //Displaying budgets in a listView
        budgetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = parent.getItemAtPosition(position).toString();

                Cursor data = dbHelper.getBudgetID(name);

                int budgetID = -1;

                while (data.moveToNext()){

                    budgetID = data.getInt(0);
                }

                List details = dbHelper.getBudgetDetails(budgetID);

                if (budgetID > -1){

                    Intent intent = new Intent(Budget.this, ViewBudget.class);
                    intent.putExtra("budgetName",name);
                    intent.putExtra("budgetId",budgetID);
                    intent.putExtra("budgetAmount",String.valueOf(details.get(1)));
                    startActivity(intent);
                }else{

                    toastMessage("There is an error");
                }

            }
        });



    }

    private void toastMessage(String message) {

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.add_budget, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {case R.id.add:
                    Intent intent = new Intent(Budget.this, AddBudget.class);
                    startActivity(intent);
                    Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.serach:
                    Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }


    }


