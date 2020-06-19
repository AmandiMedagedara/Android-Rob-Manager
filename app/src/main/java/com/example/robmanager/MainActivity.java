package com.example.robmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {

    //declaring listView
    ListView listView;

    //declaring types of services that user can interact with
    String title[] = {"Task Manager", "Budget Manager", "Transaction Manager"};
    String desc[] = {"create and manage tasks", "create and manage budgets", "create and manage transaction"};
    int image[] = {R.drawable.task,R.drawable.purse,R.drawable.transaction};

    private String un;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        un = getIntent().getStringExtra("userName");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        //assign listView to a variable
        listView = findViewById(R.id.listView);

        //creating a adapter Object and parse service title,service description and image
        MyAdapter adapter = new MyAdapter(this,title,desc,image);

        //set adapter
        listView.setAdapter(adapter);

        //when user click am item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //checking its position and redirect to particular service
                switch (position){
                    case 0:
                        //if user select first item then redirect to TaskActivity
                        Intent intent = new Intent(MainActivity.this,TaskActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //if user select second item then redirect to Budget Activity
                        Intent bIntent = new Intent(MainActivity.this,Budget.class);
                        startActivity(bIntent);
                        break;
                    case 2:
                        //if user select third item then redirect to TransactionActivity
                        Intent i = new Intent(MainActivity.this,TransactionActivity.class);
                        startActivity(i);

                        break;
                }
            }
        });

    }

    //set menu icons to ToolBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    //when user select an icon
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                Intent i = new Intent(MainActivity.this,Profile.class);
                startActivity(i);
                return true;
            case R.id.about:
                openDialog();
                return true;
            case R.id.start_intro:
                Intent intent = new Intent(MainActivity.this, Intro.class);
                intent.putExtra("userName",un);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                startActivity(intent);
                return true;
        }
        return false;
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImages[];

        MyAdapter(Context c, String title[],String description[],int images[]) {
            super(c,R.layout.item,R.id.textView,title);
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

    //Open AboutDialog function
    public void openDialog(){
        AboutDialog dialog = new AboutDialog();
        dialog.show(getSupportFragmentManager(),"AboutDialog");
    }

    //Customized toast function
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
