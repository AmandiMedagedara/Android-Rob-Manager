package com.example.robmanager.BudgetDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BudgetInfo.db";

    public DBHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
        public void onCreate(SQLiteDatabase db) {
            String SQL_CREATE_ENTRIES = "CREATE TABLE " + BudgetMaster.Budget.TABLE_NAME + " (" +
                    BudgetMaster.Budget._ID + " INTEGER PRIMARY KEY," +
                    BudgetMaster.Budget.COLUMN_NAME_BUDGET_NAME + " TEXT," +
                    BudgetMaster.Budget.COLUMN_NAME_BUDGET_AMOUNT + " INTEGER)";

            db.execSQL(SQL_CREATE_ENTRIES);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

//        Adding budget information
        public void addInfo(String BudgetName, Integer BudgetAmount){
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(BudgetMaster.Budget.COLUMN_NAME_BUDGET_NAME,BudgetName);
            values.put(BudgetMaster.Budget.COLUMN_NAME_BUDGET_AMOUNT,BudgetAmount);

            long newRowId = db.insert(BudgetMaster.Budget.TABLE_NAME, null, values);

        }


//Reading budget information
    public List readAllInfo(){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BudgetMaster.Budget._ID,
                BudgetMaster.Budget.COLUMN_NAME_BUDGET_NAME,
                BudgetMaster.Budget.COLUMN_NAME_BUDGET_AMOUNT
        };



        Cursor cursor = db.query(
                BudgetMaster.Budget.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List budgets = new ArrayList<>();

        while (cursor.moveToNext()){
            String budget1 = cursor.getString(cursor.getColumnIndexOrThrow(BudgetMaster.Budget.COLUMN_NAME_BUDGET_NAME));


            budgets.add(budget1);

        }

        cursor.close();
        return budgets;
    }

    //getting budget id
    public Cursor getBudgetID(String budgetName){
        SQLiteDatabase db = this.getWritableDatabase();

        String q = "SELECT _id FROM Budget WHERE Budget_name = \"" + budgetName + "\"";

        Cursor data = db.rawQuery(q, null);
        return data;


    }

    //getting budget details to an arraylist
    public List getBudgetDetails(int budgetID){

        SQLiteDatabase db = this.getWritableDatabase();

        String q = "SELECT * FROM Budget WHERE _id = \"" + budgetID + "\"";

        Cursor data = db.rawQuery(q, null);

        List budgetDetails = new ArrayList<>();

        while (data.moveToNext()){
            String budgetName = data.getString(data.getColumnIndexOrThrow(BudgetMaster.Budget.COLUMN_NAME_BUDGET_NAME));
            String budgetAmount = data.getString(data.getColumnIndexOrThrow(BudgetMaster.Budget.COLUMN_NAME_BUDGET_AMOUNT));


            budgetDetails.add(budgetName);
            budgetDetails.add(budgetAmount);
        }

        data.close();
        return budgetDetails;
    }

    //deleting a budget from the database
    public void deleteBudget(String budgetName){

        SQLiteDatabase db = getReadableDatabase();


        String selection = BudgetMaster.Budget.COLUMN_NAME_BUDGET_AMOUNT + " LIKE ?";

        String[] selectionArgs = {budgetName};

        db.delete(BudgetMaster.Budget.TABLE_NAME,selection,selectionArgs);
    }


    //updating budgets
    public int updateBudget(String oldBudgetName,String newBudgetName){

        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(BudgetMaster.Budget.COLUMN_NAME_BUDGET_NAME,newBudgetName);
//        values.put(BudgetMaster.Budget.C,newBudgetName);

        String selection = BudgetMaster.Budget.COLUMN_NAME_BUDGET_NAME + "LIKE ?";
        String[] selectionArgs = {oldBudgetName};

        int count = db.update(
                BudgetMaster.Budget.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        return count;


    }

//updating the database
    public int update(String ID, String name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(BudgetMaster.Budget.COLUMN_NAME_BUDGET_NAME,name);

        //updating row
        int count = sqLiteDatabase.update(BudgetMaster.Budget.TABLE_NAME, values, "_id=" + ID, null);
        sqLiteDatabase.close();
        return count;
    }

}

