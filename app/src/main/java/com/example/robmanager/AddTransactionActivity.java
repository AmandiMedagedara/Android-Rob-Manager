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

import com.example.robmanager.TransactionDatabase.TrDBHelper;

public class AddTransactionActivity extends AppCompatActivity {
    TrDBHelper trDB;
    EditText editname,editdescrip,editamount;
    Button btnadddata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        trDB = new TrDBHelper(this);

        editname = (EditText)findViewById(R.id.Ex_namefill);
        editdescrip = (EditText)findViewById(R.id.Ex_discfill);
        editamount = (EditText)findViewById(R.id.Ex_amountfill);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_ex,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_ex:

                String a = editname.getText().toString();
                String b = editdescrip.getText().toString();
                String c = editamount.getText().toString();
                if(editname.length() != 0 && editamount.length() !=0){
                    addData(a,b,c);
                    Intent intent = new Intent(AddTransactionActivity.this,ViewExpensesActivity.class);
                    startActivity(intent);
                    toastMessage("Saved");
                }else {
                    if(editname.length() == 0 && editamount.length() != 0)
                        toastMessage("You must provide a name!");
                    else if(editamount.length() == 0 && editname.length() != 0)
                        toastMessage("You must provide Amount!");
                    else
                        toastMessage("You must provide Details!");
                }
                return true;
        }
        return false;
    }
    public void addData(String newexname, String newexdesc, String newexamount){
        trDB.addInfo(newexname, newexdesc, newexamount);
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
