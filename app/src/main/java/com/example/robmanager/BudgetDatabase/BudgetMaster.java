package com.example.robmanager.BudgetDatabase;

import android.provider.BaseColumns;

public final class BudgetMaster {

    private BudgetMaster(){}
    protected static class Budget implements BaseColumns{
        public static final String TABLE_NAME = "Budget";
        public static final String COLUMN_NAME_BUDGET_NAME = "Budget_name";
        public static final String COLUMN_NAME_BUDGET_AMOUNT = "Budget_amount";
    }
}
