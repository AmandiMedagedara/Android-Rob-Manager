package com.example.robmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.robmanager.TaskDatabase.DBHelper;
import com.example.robmanager.TransactionDatabase.TrDBHelper;

public class ExpenseEditingActivity extends AppCompatActivity {

    TrDBHelper TrDB;
    String expenseAmount,expenseDesc,expenseName,toUpdate1,toUpdate2,toUpdate3;
    int expenseID;
    EditText editName,editAmount,editDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_editing);


        TrDB =  new TrDBHelper(this);


        expenseName = getIntent().getStringExtra("Ex_nme");
        expenseID = getIntent().getIntExtra("EX_Id",-1);
        expenseAmount = getIntent().getStringExtra("EX_des");
        expenseDesc = getIntent().getStringExtra("EX_amount");



        editName = findViewById(R.id.editEx_namefill);
        editAmount = findViewById(R.id.editEx_amountfill);
        editDesc =findViewById(R.id.editEx_discfill);

        editName.setText(expenseName);
        editAmount.setText(expenseAmount);
        editDesc.setText(expenseDesc);

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveedex:
                //when user select save icon and get user input and assign to a variable
                toUpdate1 = editName.getText().toString();
                toUpdate2 = editAmount.getText().toString();
                toUpdate3 = editDesc.getText().toString();

                //check weather user input is empty or not
                if(toUpdate1.length() != 0){

                    if(toUpdate1.length() < 15) {

                        //then insert that value with name and id
                        int res = TrDB.update(String.valueOf(expenseID), toUpdate1,toUpdate2,toUpdate3);

                        //check weather it is updated or not
                        if (res > 0) {

                            //if it is updated then print a message with Toast
                            toastMessage("Successfully updated");

                            //redirect user to TaskActivity
                            Intent intent = new Intent(ExpenseEditingActivity.this, ViewExpensesActivity.class);
                            intent.putExtra("expenseName", toUpdate1);
                            intent.putExtra("expenseamount", toUpdate2);
                            intent.putExtra("expensedesc", toUpdate3);
                            intent.putExtra("expenseId", expenseID);
                            startActivity(intent);
                        } else {

                            //if it is not updated generate an error message to user
                            toastMessage("There is an error with updating");
                        }

                    }
                }

                return true;

            case R.id.deleteedex:
                TrDB.deleteExpense(expenseName);
                Intent intent2 = new Intent(ExpenseEditingActivity.this,ViewExpensesActivity.class);
                startActivity(intent2);

                return true;
        }
        return false;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editing_ex,menu);
        return true;
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
