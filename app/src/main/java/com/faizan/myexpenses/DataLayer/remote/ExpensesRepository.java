package com.faizan.myexpenses.DataLayer.remote;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.faizan.myexpenses.DataLayer.database.MyDatabase;
import com.faizan.myexpenses.DataLayer.model.Expense;

import java.util.List;

public class ExpensesRepository {

    private MyDatabase myDatabase;

    public ExpensesRepository(Context context) {
        myDatabase = MyDatabase.getInstance(context);
    }

    // Expenses
    public void insertExpenses(Expense expense) {
        myDatabase.expensesDao().insertExpense(expense);
    }

    public LiveData<List<Expense>> getAllExpensesD(String month) {
        return myDatabase.expensesDao().getAllExpense(month);
    }


    public void deleteMonthlyExpenses(Expense expense) {
        myDatabase.expensesDao().deleteExpenses(expense);
    }

    public void updateMonthlyExpense(Expense expense) {
        myDatabase.expensesDao().updateExpense(expense);

    }
}


