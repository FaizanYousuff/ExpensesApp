package com.faizan.myexpenses.DataLayer.remote;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.faizan.myexpenses.DataLayer.database.MyDatabase;
import com.faizan.myexpenses.DataLayer.model.MonthlyExpense;

import java.util.List;

public class MonthlyExpenseRepository {

    private Context context;

    private MyDatabase myDatabase;

    public MonthlyExpenseRepository(Context context) {
        this.context = context;
        myDatabase = MyDatabase.getInstance(context);
    }

    public void insertMonthlyExpense(MonthlyExpense monthlyExpense) {
        myDatabase.monthlyExpenseDao().insertMonthlyExpense(monthlyExpense);
    }

    public void deleteMonthlyExpenses(MonthlyExpense monthlyExpense) {
        myDatabase.monthlyExpenseDao().deleteMonthlyExpenses(monthlyExpense);
    }

    public void updateMonthlyExpense(MonthlyExpense monthlyExpense) {
        myDatabase.monthlyExpenseDao().updateMonthlyExpense(monthlyExpense);

    }

    public LiveData<List<MonthlyExpense>> getAllMonthlyExpenses() {
        return myDatabase.monthlyExpenseDao().getAllMonthlyExpenses();
    }

    public MonthlyExpense getMonthlyExpense(int month) {
        return myDatabase.monthlyExpenseDao().getMonthlyExpense(month);
    }

    public void updateSpentAmount(String spent, int month) {
        myDatabase.monthlyExpenseDao().updateSpentAmount(spent, month);
    }
}
