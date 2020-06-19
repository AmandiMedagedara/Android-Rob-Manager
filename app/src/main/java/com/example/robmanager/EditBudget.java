package com.example.robmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.robmanager.BudgetDatabase.DBHelper;

public class EditBudget extends AppCompatActivity {

    String name, toUpdate, amount;
    int budgetID;
    EditText editText, editText2;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);

        dbHelper = new DBHelper(this);

        name = getIntent().getStringExtra("name");
        budgetID = getIntent().getIntExtra("budgetId", -1);
        amount = getIntent().getStringExtra("budgetAmount");

        editText = findViewById(R.id.budget_name);
        editText2 = findViewById(R.id.budget_editText);

        editText.setText(name);
        editText2.setText(amount);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                toUpdate = editText.getText().toString();

                //updating budget name
                if (!toUpdate.equals("")) {

                    int res = dbHelper.update(String.valueOf(budgetID), toUpdate);

                    if (res > 0) {
                        toastMessage("Successfully updated");
                        Intent intent = new Intent(EditBudget.this, Budget.class);
                        intent.putExtra("name", toUpdate);
                        intent.putExtra("budgetId", budgetID);
                        startActivity(intent);
                    } else {
                        toastMessage("There is an error with updating");
                    }

                } else {
                    toastMessage("You must enter a name");
                }

                return true;

                //deleting budgets
            case R.id.delete:
                dbHelper.deleteBudget(amount);
                Intent i = new Intent(EditBudget.this, Budget.class);
                startActivity(i);
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_budget,menu);
        return true;
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}

