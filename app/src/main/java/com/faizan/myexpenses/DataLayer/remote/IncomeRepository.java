package com.faizan.myexpenses.DataLayer.remote;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.faizan.myexpenses.DataLayer.database.MyDatabase;
import com.faizan.myexpenses.DataLayer.model.Expense;
import com.faizan.myexpenses.DataLayer.model.Income;

import java.util.List;

public class IncomeRepository {

    private Context context;

    private MyDatabase myDatabase;

    public IncomeRepository(Context context) {
        this.context = context;
        myDatabase = MyDatabase.getInstance(context);
    }

    // Expenses
    public void insertIncome(Income income){
        myDatabase.incomeDao().insertIncome(income);
    }

    public LiveData<List<Income>> getAllIncome(){
        return  myDatabase.incomeDao().getAllIncome();
    }

}
