package com.faizan.myexpenses.DataLayer.remote;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.faizan.myexpenses.DataLayer.database.MyDatabase;
import com.faizan.myexpenses.DataLayer.model.DetailOtherExpense;

import java.util.List;

public class DetailOtherExpenseRepository {

    private MyDatabase myDatabase;

    public DetailOtherExpenseRepository(Context context) {
        myDatabase = MyDatabase.getInstance(context);
    }

    // Expenses
    public void insertDetailOtherExpense(DetailOtherExpense expense) {
        myDatabase.detailExpenseDao().insertDetailExpense(expense);
    }

    public LiveData<List<DetailOtherExpense>> getDetailOtherExpense(int month) {
        return myDatabase.detailExpenseDao().getAllDetailExpense(month);
    }


    public void deleteDetailOtherExpense(DetailOtherExpense expense) {
        myDatabase.detailExpenseDao().deleteDetailExpense(expense);
    }

    public void updateDetailOtherExpense(DetailOtherExpense expense) {
        myDatabase.detailExpenseDao().updateDetailExpense(expense);

    }
}
