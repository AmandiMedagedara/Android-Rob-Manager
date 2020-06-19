package com.example.robmanager.TaskDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TaskInfo.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TaskMaster.Tasks.TABLE_NAME + " (" +
                        TaskMaster.Tasks._ID + " INTEGER PRIMARY KEY," +
                        TaskMaster.Tasks.COLUMN_NAME_TaskName + " TEXT," +
                        TaskMaster.Tasks.COLUMN_NAME_YEAR + " TEXT," +
                        TaskMaster.Tasks.COLUMN_NAME_Month + " TEXT," +
                        TaskMaster.Tasks.COLUMN_NAME_Day + " TEXT," +
                        TaskMaster.Tasks.COLUMN_NAME_Hour + " TEXT," +
                        TaskMaster.Tasks.COLUMN_NAME_Minute + " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addInfo(String taskName,String year,String month,String day,String hour,String minute) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TaskMaster.Tasks.COLUMN_NAME_TaskName, taskName);
        values.put(TaskMaster.Tasks.COLUMN_NAME_YEAR, year);
        values.put(TaskMaster.Tasks.COLUMN_NAME_Month, month);
        values.put(TaskMaster.Tasks.COLUMN_NAME_Day, day);
        values.put(TaskMaster.Tasks.COLUMN_NAME_Hour, hour);
        values.put(TaskMaster.Tasks.COLUMN_NAME_Minute, minute);

        long newRowId = db.insert(TaskMaster.Tasks.TABLE_NAME,null,values);

    }

    public List readAllInfo(){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                TaskMaster.Tasks._ID,
                TaskMaster.Tasks.COLUMN_NAME_TaskName
        };



        Cursor cursor = db.query(
                TaskMaster.Tasks.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List tasks = new ArrayList<>();

        while (cursor.moveToNext()){
            String task = cursor.getString(cursor.getColumnIndexOrThrow(TaskMaster.Tasks.COLUMN_NAME_TaskName));

            tasks.add(task);
        }

        cursor.close();
        return tasks;
    }

    public void deleteTask(String taskName){

        SQLiteDatabase db = getReadableDatabase();

        String selection = TaskMaster.Tasks.COLUMN_NAME_TaskName + " LIKE ?";

        String[] selectionArgs = {taskName};

        db.delete(TaskMaster.Tasks.TABLE_NAME,selection,selectionArgs);
    }

    public Cursor getTaskID(String taskName){
        SQLiteDatabase db = this.getWritableDatabase();

        String q = "SELECT _id FROM Task WHERE taskName = \"" + taskName + "\"";

        Cursor data = db.rawQuery(q, null);
        return data;


    }

    public List getTaskDetails(int taskId){
        SQLiteDatabase db = this.getWritableDatabase();

        String q = "SELECT * FROM Task WHERE _id = \"" + taskId + "\"";

        Cursor data = db.rawQuery(q, null);


        List details = new ArrayList<>();

        while (data.moveToNext()){
            String task = data.getString(data.getColumnIndexOrThrow(TaskMaster.Tasks.COLUMN_NAME_TaskName));
            String year = data.getString(data.getColumnIndexOrThrow(TaskMaster.Tasks.COLUMN_NAME_YEAR));
            String month = data.getString(data.getColumnIndexOrThrow(TaskMaster.Tasks.COLUMN_NAME_Month));
            String day = data.getString(data.getColumnIndexOrThrow(TaskMaster.Tasks.COLUMN_NAME_Day));
            String hour = data.getString(data.getColumnIndexOrThrow(TaskMaster.Tasks.COLUMN_NAME_Hour));
            String minute = data.getString(data.getColumnIndexOrThrow(TaskMaster.Tasks.COLUMN_NAME_Minute));


            details.add(task);
            details.add(year);
            details.add(month);
            details.add(day);
            details.add(hour);
            details.add(minute);

        }

        data.close();
        return details;
    }

    public int update(String ID, String name,String year,String month,String day,String hour,String minute) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(TaskMaster.Tasks.COLUMN_NAME_TaskName,name);
        values.put(TaskMaster.Tasks.COLUMN_NAME_YEAR,year);
        values.put(TaskMaster.Tasks.COLUMN_NAME_Month,month);
        values.put(TaskMaster.Tasks.COLUMN_NAME_Day,day);
        values.put(TaskMaster.Tasks.COLUMN_NAME_Hour,hour);
        values.put(TaskMaster.Tasks.COLUMN_NAME_Minute,minute);

        //updating row
        int count = sqLiteDatabase.update(TaskMaster.Tasks.TABLE_NAME, values, "_id=" + ID, null);
        sqLiteDatabase.close();
        return count;
    }

}
