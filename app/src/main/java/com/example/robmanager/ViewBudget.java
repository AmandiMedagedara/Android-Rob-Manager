package com.example.robmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ViewBudget extends AppCompatActivity {

    String name,amount;
    int budgetID;
    TextView value,budget_Amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_budget);

        //Displaying budgetName and BudgetAmount
        name = getIntent().getStringExtra("budgetName");
        budgetID = getIntent().getIntExtra("budgetId",-1);
        amount = getIntent().getStringExtra("budgetAmount");
        value = findViewById(R.id.budgetValue);
        budget_Amount = findViewById(R.id.budgetAmount);

        value.setText(name);
        budget_Amount.setText(amount);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_budget,menu);
        return true;
    }

    //EditPen OnOptionSelected Method
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.editPen:
                Intent intent = new Intent(ViewBudget.this,EditBudget.class);
                intent.putExtra("name",name);
                intent.putExtra("budgetId",budgetID);
                intent.putExtra("budgetAmount",amount);
                startActivity(intent);
                Toast.makeText(this,"Edit Pen",Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}
