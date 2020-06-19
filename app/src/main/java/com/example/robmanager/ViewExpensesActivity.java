package com.example.robmanager;

import android.annotation.SuppressLint;
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

import com.example.robmanager.TransactionDatabase.TrDBHelper;

import java.util.List;

public class ViewExpensesActivity extends AppCompatActivity {

    ListView expensesListView;
    TrDBHelper trDB;
    ArrayAdapter<String> transactionAdapter;
    SearchView theFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);
        theFilter = findViewById(R.id.search_tr);
        expensesListView = findViewById(R.id.transaction_listView);
        trDB = new TrDBHelper(this);

        List data = trDB.readAllInfo();

        transactionAdapter = new ArrayAdapter<String>(this,R.layout.transactions_layout,R.id.transaction_textView,data);
        expensesListView.setAdapter(transactionAdapter);

        registerForContextMenu(expensesListView);

        expensesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = parent.getItemAtPosition(position).toString();

                Cursor data = trDB.getexpenseID(name);


                int ExID = -1;

                while (data.moveToNext()) {
                    ExID = data.getInt(0);
                }

                List details = trDB.getexpensesDetails((ExID));

                if (ExID > -1) {
                    Intent intent = new Intent(ViewExpensesActivity.this, EditExActivity.class);
                    intent.putExtra("ExName", name);
                    intent.putExtra("ID", ExID);
                    intent.putExtra("ExAmount", String.valueOf(details.get(2)));
                    intent.putExtra("ExDesc", String.valueOf(details.get(1)));
                    startActivity(intent);
                } else {
                    toastMessage("There is an error!");
                }

            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(ViewExpensesActivity.this, AddTransactionActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_menu, menu);

        MenuItem searchExpense = menu.findItem(R.id.search);
        androidx.appcompat.widget.SearchView SearchView = (androidx.appcompat.widget.SearchView) searchExpense.getActionView();

        SearchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override

            public boolean onQueryTextChange(String newText) {
                transactionAdapter.getFilter().filter(newText);
                return false;
            }

        });

        return true;
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
