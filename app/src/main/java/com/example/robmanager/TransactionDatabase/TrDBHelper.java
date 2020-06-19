package com.example.robmanager.TransactionDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;
import java.util.List;

public class TrDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Transactions.db";
    public static final int DATABASE_VERSION = 1;

    public TrDBHelper(Context context) {
        super(context,DATABASE_NAME,null ,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE "+ ExpensesMaster.Expenses.TABLE_NAME + " (" +
                ExpensesMaster.Expenses._ID+ " INTEGER PRIMARY KEY," +
                ExpensesMaster.Expenses.COLUMN_NAME_ExName+ " TEXT," +
                ExpensesMaster.Expenses.COLUMN_NAME_ExDesc+" TEXT," +
                ExpensesMaster.Expenses.COLUMN_NAME_ExAmount + " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addInfo(String ExName, String ExDesc, String ExDate) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ExpensesMaster.Expenses.COLUMN_NAME_ExName, ExName);
        values.put(ExpensesMaster.Expenses.COLUMN_NAME_ExDesc, ExDesc);
        values.put(ExpensesMaster.Expenses.COLUMN_NAME_ExAmount, ExDate);

        long newRowId = db.insert(ExpensesMaster.Expenses.TABLE_NAME,null,values);

    }

    public List readAllInfo(){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                ExpensesMaster.Expenses._ID,
                ExpensesMaster.Expenses.COLUMN_NAME_ExName
        };



        Cursor cursor = db.query(
                ExpensesMaster.Expenses.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List expenses = new ArrayList<>();

        while (cursor.moveToNext()){
            String EXName = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesMaster.Expenses.COLUMN_NAME_ExName));
            expenses.add(EXName);
        }

        cursor.close();
        return expenses;
    }
    public Cursor getexpenseID(String ExName){
        SQLiteDatabase db = this.getWritableDatabase();

        String q = "SELECT _ID FROM Expense WHERE ExName = \"" + ExName + "\"";

        Cursor data = db.rawQuery(q, null);

        return data;


    }
    public void deleteExpense(String ExName){

        SQLiteDatabase db = getReadableDatabase();

        String selection = ExpensesMaster.Expenses.COLUMN_NAME_ExName + " LIKE ?";

        String[] selectionArgs = {ExName};

        db.delete(ExpensesMaster.Expenses.TABLE_NAME,selection,selectionArgs);
    }
    public List getexpensesDetails(int ExID){
        SQLiteDatabase db = this.getWritableDatabase();

        String ex = "SELECT * FROM Expense WHERE _id = \"" + ExID + "\"";

        Cursor data = db.rawQuery(ex,null);

        List expensesDetails = new ArrayList<>();

        while(data.moveToNext()){

            String exName = data.getString(data.getColumnIndexOrThrow((ExpensesMaster.Expenses.COLUMN_NAME_ExName)));
            String exAmount = data.getString(data.getColumnIndexOrThrow((ExpensesMaster.Expenses.COLUMN_NAME_ExAmount)));
            String exDesc = data.getString(data.getColumnIndexOrThrow((ExpensesMaster.Expenses.COLUMN_NAME_ExDesc)));

            expensesDetails.add(exName);
            expensesDetails.add(exAmount);
            expensesDetails.add(exDesc);
        }
        data.close();
        return expensesDetails;

    }
    public int update(String ID, String name,String amount,String desc) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(ExpensesMaster.Expenses.COLUMN_NAME_ExName,name);
        values.put(ExpensesMaster.Expenses.COLUMN_NAME_ExAmount,amount);
        values.put(ExpensesMaster.Expenses.COLUMN_NAME_ExDesc,desc);

        int count = sqLiteDatabase.update(ExpensesMaster.Expenses.TABLE_NAME, values, "_ID=" + ID, null);
        sqLiteDatabase.close();
        return count;
    }
}
