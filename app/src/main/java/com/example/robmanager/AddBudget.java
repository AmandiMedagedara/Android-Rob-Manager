package com.example.robmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.robmanager.BudgetDatabase.DBHelper;

public class AddBudget extends AppCompatActivity {

    DBHelper dbHelper;
    private Button add;
    private EditText budget, bName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        dbHelper = new DBHelper(this);

        budget = findViewById(R.id.BAmount);
        bName = findViewById(R.id.bName);

            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save,menu);
        return true;
    }


    //Save item selected method
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:

                String newBudget = bName.getText().toString();
                Integer newAmount = Integer.parseInt(budget.getText().toString());

                //Condition for adding budget details
                if (budget.length() != 0) {


                    dbHelper.addInfo(newBudget, newAmount);

                    Intent intent = new Intent(AddBudget.this, Budget.class);
                    startActivity(intent);
                    toastMessage("Successfully added");
                } else {
                    toastMessage("You must provide valid budget!");
                }
                return true;
        }
        return false;
    }

        public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
}