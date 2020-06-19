package com.example.robmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TransactionActivity extends AppCompatActivity {
    ListView listView;

    String title[] = {"EXPENSES", "REVENUES"};
    String desc[] = {"Note what you spent", "Note your Gains"};
    int image[] = {R.drawable.expenses, R.drawable.revenues};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        listView = findViewById(R.id.listView2);

        TransactionActivity.MyAdapter adapter = new TransactionActivity.MyAdapter(this,title,desc,image);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent1 = new Intent(TransactionActivity.this,ViewExpensesActivity.class);
                        startActivity(intent1);
                        Toast.makeText(TransactionActivity.this,"EXPENSES",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
//                        Intent intent2 = new Intent(TransactionActivity.this,ViewRevenuesActivity.class);
//                        startActivity(intent2);
                        Toast.makeText(TransactionActivity.this,"REVENUES",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImages[];

        MyAdapter(Context c, String title[],String description[],int images[]) {
            super(c, R.layout.item, R.id.textView,title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImages = images;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item  = layoutInflater.inflate(R.layout.item,parent,false);
            ImageView images = item.findViewById(R.id.itemImage);
            TextView myTitle = item.findViewById(R.id.textView);
            TextView myDescription = item.findViewById(R.id.textView2);

            images.setImageResource(rImages[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);


            return item;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transaction_menu,menu);
        return true;
    }




}
