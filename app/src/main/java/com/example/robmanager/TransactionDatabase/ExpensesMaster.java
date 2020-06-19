package com.example.robmanager.TransactionDatabase;

import android.provider.BaseColumns;

public final class ExpensesMaster {

    private ExpensesMaster(){};

    protected static class Expenses implements BaseColumns {

        public static final String TABLE_NAME = "Expense";
        public static final String COLUMN_NAME_ExName = "ExName";
        public static final String COLUMN_NAME_ExDesc = "ExDesc";
        public static final String COLUMN_NAME_ExAmount = "ExAmount";
    }
}
