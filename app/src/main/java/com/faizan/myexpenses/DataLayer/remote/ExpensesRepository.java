package com.faizan.myexpenses.DataLayer.remote;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.faizan.myexpenses.DataLayer.database.MyDatabase;
import com.faizan.myexpenses.DataLayer.model.Credits;
import com.faizan.myexpenses.DataLayer.model.Expense;

import java.util.List;

public class ExpensesRepository {
    private Context context;

    private MyDatabase myDatabase;

    public ExpensesRepository(Context context) {
        this.context = context;
        myDatabase = MyDatabase.getInstance(context);
    }

    // Expenses
    public void insertExpenses(Expense expense){
        myDatabase.expensesDao().insertExpense(expense);
    }

    public LiveData<List<Expense>> getAllExpenses(){
        return  myDatabase.expensesDao().getAllExpense();
    }

}


