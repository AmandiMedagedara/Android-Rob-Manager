package com.example.robmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.robmanager.TransactionDatabase.TrDBHelper;

public class EditExActivity extends AppCompatActivity {

    String ExpenName,ExAmount,ExDesc;
    private AlertDialog.Builder alert;
    int ExId;
    TextView Ex_value,Ex_AmValue,Ex_DeValue;
    TrDBHelper TrDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expenses);

        TrDB =  new TrDBHelper(this);

        ExpenName = getIntent().getStringExtra("ExName");
        ExId = getIntent().getIntExtra("ID",-1);
        ExAmount = getIntent().getStringExtra("ExAmount");
        ExDesc = getIntent().getStringExtra("ExDesc");

        Ex_value = findViewById(R.id.Ex_Value);
        Ex_AmValue = findViewById(R.id.Ex_AmValue);
        Ex_DeValue = findViewById(R.id.Ex_DeValue);

        Ex_value.setText(ExpenName);
        Ex_AmValue.setText(ExAmount);
        Ex_DeValue.setText(ExDesc);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_expenses,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.editex:
                Intent intent = new Intent(EditExActivity.this,ExpenseEditingActivity.class);
                intent.putExtra("Ex_nme",ExpenName);
                intent.putExtra("EX_Id",ExId);
                intent.putExtra("EX_amount",ExAmount);
                intent.putExtra("EX_des",ExDesc);
                startActivity(intent);
                return true;

            case R.id.deleteex:
                TrDB.deleteExpense(ExpenName);
                Intent intent2 = new Intent(EditExActivity.this,ViewExpensesActivity.class);
                startActivity(intent2);

                return true;
        }

        return false;
    }

}
