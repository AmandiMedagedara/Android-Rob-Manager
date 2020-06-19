package com.example.robmanager.TaskDatabase;

import android.provider.BaseColumns;

public final class TaskMaster {

    private TaskMaster(){};

    protected static class Tasks implements BaseColumns {

        public static final String TABLE_NAME  = "Task";
        public static final String COLUMN_NAME_TaskName = "taskName";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_Month = "month";
        public static final String COLUMN_NAME_Day = "day";
        public static final String COLUMN_NAME_Hour = "hour";
        public static final String COLUMN_NAME_Minute = "minute";

    }
}
