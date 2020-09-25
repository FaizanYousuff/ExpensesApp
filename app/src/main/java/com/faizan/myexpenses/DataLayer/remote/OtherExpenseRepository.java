package com.faizan.myexpenses.DataLayer.remote;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.faizan.myexpenses.DataLayer.database.MyDatabase;
import com.faizan.myexpenses.DataLayer.model.OtherExpense;

import java.util.List;

public class OtherExpenseRepository {

    private MyDatabase myDatabase;

    public OtherExpenseRepository(Context context) {
        myDatabase = MyDatabase.getInstance(context);
    }

    public void insertOtherExpense(OtherExpense otherExpense) {
        myDatabase.otherExpenseDao().insertOtherExpense(otherExpense);
    }

    public void deleteOtherExpense(OtherExpense otherExpense) {
        myDatabase.otherExpenseDao().deleteOtherExpenses(otherExpense);
    }

    public void updateOtherExpense(OtherExpense otherExpense) {
        myDatabase.otherExpenseDao().updateOtherExpense(otherExpense);

    }

    public LiveData<List<OtherExpense>> getAllOtherExpenses() {
        return myDatabase.otherExpenseDao().getAllOtherExpenses();
    }

    public OtherExpense getOtherExpense(int id) {
        return myDatabase.otherExpenseDao().getOtherExpense(id);
    }

    public void updateSpentAmount(int spent, int id) {
        myDatabase.otherExpenseDao().updateSpentAmount(spent, id);
    }
}
